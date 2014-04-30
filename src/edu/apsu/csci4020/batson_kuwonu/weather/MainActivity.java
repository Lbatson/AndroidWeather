package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity implements SearchDialogFragment.SearchDialogListener, WeatherAPI.WeatherAPIListener {

    private WeatherAPI weatherAPI;
    private WeatherAPI.DownloadTask downloadTask;

    public TextView mainTemperatureText;
    public ListView dailyForecastListView;
    public RelativeLayout progressLayout;
    public ArrayList<DailyForecast> dailyForecastArray;
    public DailyAdapter dailyAdapter;
    public ArrayAdapter<DailyForecast> dailyForecastArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // View setup
        mainTemperatureText = (TextView) findViewById(R.id.tv_main_temp);
        dailyForecastListView = (ListView) findViewById(R.id.lv_daily_forecast);
        progressLayout = (RelativeLayout) findViewById(R.id.layout_progress);

        // Data setup
        weatherAPI = new WeatherAPI(this);
        dailyForecastArray = new ArrayList<DailyForecast>();
        dailyAdapter = new DailyAdapter(this, dailyForecastArray);
        dailyForecastListView.setAdapter(dailyAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop retrieving weather data
        if (downloadTask != null) {
            downloadTask.cancel(true);
            downloadTask = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // Present custom search dialog
            case R.id.action_search:
                FragmentManager fm = getFragmentManager();
                SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
                searchDialogFragment.show(fm, "fragment_search");
                return true;
            // Present gps dialog
            case R.id.action_gps:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSearchPositiveClick(DialogFragment dialogFragment, String zip_code_value) {
        // Request weather data from zip code entry
        progressLayout.setVisibility(View.VISIBLE);
        downloadTask = weatherAPI.retrieveWeatherInfo(zip_code_value);
    }

    @Override
    public void onWeatherInfoRetrieved(JSONObject responseData, boolean error) {
        progressLayout.setVisibility(View.INVISIBLE);
        if (error) {
            displayCurrentConditions(responseData, error);
        } else {
            displayCurrentConditions(responseData, false);
            displayHourlyForecast(responseData);
            displayDailyForecast(responseData);
        }
    }

    public void displayCurrentConditions(JSONObject data, boolean error) {
        try {
            if (error) {
                mainTemperatureText.setText("N/A");
            } else {
                JSONObject current = data.getJSONObject("current_observation");
                // Fahrenheit u2109
                // Celcius u2103
                mainTemperatureText.setText(current.getInt("temp_f") + "\u2109");
            }
        } catch (JSONException e) {

        }
    }

    public void displayHourlyForecast(JSONObject data) {

    }

    public void displayDailyForecast(JSONObject data) {
        try {
            // Remove data
            dailyForecastArray.clear();

            // Get forecastday array of objects
            JSONObject forecast = data.getJSONObject("forecast");
            JSONObject simpleforecast = forecast.getJSONObject("simpleforecast");
            JSONArray forecastday = simpleforecast.getJSONArray("forecastday");

            for (int i = 0; i < forecastday.length(); i++) {
                // Get JSON objects
                JSONObject row = forecastday.getJSONObject(i);
                JSONObject date = row.getJSONObject("date");
                JSONObject high = row.getJSONObject("high");
                JSONObject low = row.getJSONObject("low");

                // Get necessary values for daily forecast
                String weekday = date.getString("weekday");
                int day = date.getInt("day");
                String conditions = row.getString("icon");
                int high_f = high.getInt("fahrenheit");
                int high_c  = high.getInt("celsius");
                int low_f = low.getInt("fahrenheit");
                int low_c = low.getInt("celsius");

                // Add each day's forecast to list
                DailyForecast dailyForecast = new DailyForecast(weekday, day, conditions, high_f, high_c, low_f, low_c);
                dailyForecastArray.add(dailyForecast);
            }

            // Update listview array adapter
            dailyAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
