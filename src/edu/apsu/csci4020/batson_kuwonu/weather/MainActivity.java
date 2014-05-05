package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.*;
import com.google.android.gms.location.LocationClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends Activity implements
        SearchDialogFragment.SearchDialogListener,
        LocationDialogFragment.LocationDialogListener,
        WeatherAPI.WeatherAPIListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

    private WeatherAPI weatherAPI;
    private WeatherAPI.DownloadTask downloadTask;
    private LocationClient locationClient;
    private Location location;
    private boolean isConnected;

    public TextView currentLocationText;
    public TextView mainTemperatureText;
    public TextView weatherConditionText;
    public TextView hourlyTitleText;
    public ListView hourlyForecastListView;
    public TextView dailyTitleText;
    public ListView dailyForecastListView;
    public RelativeLayout background;
    public RelativeLayout progressLayout;
    public Conditions currentConditions;
    public ArrayList<HourlyForecast> hourlyForecastArray;
    public HourlyAdapter hourlyAdapter;
    public ArrayList<DailyForecast> dailyForecastArray;
    public DailyAdapter dailyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // View setup
        currentLocationText = (TextView) findViewById(R.id.tv_location);
        mainTemperatureText = (TextView) findViewById(R.id.tv_main_temp);
        weatherConditionText = (TextView) findViewById(R.id.tv_weather_condition);
        hourlyTitleText = (TextView) findViewById(R.id.tv_hourly_forecast);
        hourlyForecastListView = (ListView) findViewById(R.id.lv_hourly_forecast);
        dailyTitleText = (TextView) findViewById(R.id.tv_daily_forecast);
        dailyForecastListView = (ListView) findViewById(R.id.lv_daily_forecast);
        background = (RelativeLayout) findViewById(R.id.rl_background);
        progressLayout = (RelativeLayout) findViewById(R.id.layout_progress);

        // Data setup
        isConnected = false;
        weatherAPI = new WeatherAPI(this);
        hourlyForecastArray = new ArrayList<HourlyForecast>();
        hourlyAdapter = new HourlyAdapter(this, hourlyForecastArray);
        hourlyForecastListView.setAdapter(hourlyAdapter);
        dailyForecastArray = new ArrayList<DailyForecast>();
        dailyAdapter = new DailyAdapter(this, dailyForecastArray);
        dailyForecastListView.setAdapter(dailyAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
            // Create location client and connect
            locationClient = new LocationClient(this, this, this);
            locationClient.connect();
        } else {
            Toast.makeText(this, "Unable to connect with Google Play Services", Toast.LENGTH_SHORT).show();
        }
        clearDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop retrieving weather data
        if (downloadTask != null) {
            downloadTask.cancel(true);
            downloadTask = null;
        }
        // Disconnect location services
        if (locationClient != null) {
            locationClient.disconnect();
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
        FragmentManager fm = getFragmentManager();
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // Present custom search dialog
            case R.id.action_search:
                SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
                searchDialogFragment.show(fm, "fragment_search");
                return true;
            // Present gps dialog
            case R.id.action_gps:
                LocationDialogFragment locationDialogFragment = new LocationDialogFragment();
                locationDialogFragment.show(fm, "fragment_location");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSearchPositiveClick(DialogFragment dialogFragment, String zip_code_value) {
        // Request weather data from zip code entry
        progressLayout.setVisibility(View.VISIBLE);
        downloadTask = weatherAPI.retrieveWeatherInfoByZip(zip_code_value);
    }


    @Override
    public void onLocationPositiveClick(DialogFragment dialogFragment) {
        if (isConnected) {
            // Request weather data from location information
            progressLayout.setVisibility(View.VISIBLE);
            location = locationClient.getLastLocation();
            downloadTask = weatherAPI.retrieveWeatherInfoByLocation(location.getLatitude(), location.getLongitude());
        } else {
            Toast.makeText(this, "Unable to get current location information", Toast.LENGTH_SHORT).show();
        }
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

    public void clearDisplay() {
        // Used to reset display if error
        currentConditions = null;
        currentLocationText.setText("");
        mainTemperatureText.setText("");
        weatherConditionText.setText("");
        hourlyTitleText.setVisibility(View.INVISIBLE);
        hourlyForecastListView.setVisibility(View.INVISIBLE);
        dailyTitleText.setVisibility(View.INVISIBLE);
        dailyForecastListView.setVisibility(View.INVISIBLE);
    }

    public void displayCurrentConditions(JSONObject data, boolean error) {
        try {
            if (error) {
                clearDisplay();
                Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();
            } else {
                // Get and display current location and conditions
                JSONObject current = data.getJSONObject("current_observation");
                JSONObject display_location = current.getJSONObject("display_location");
                currentLocationText.setText(display_location.getString("full"));
                currentConditions = new Conditions(
                        current.getString("weather"),
                        current.getInt("temp_f"),
                        current.getInt("temp_c"),
                        current.getString("icon")
                );
                // Fahrenheit u2109
                // Celcius u2103
                mainTemperatureText.setText(currentConditions.getTemp_f() + "\u2109");
                weatherConditionText.setText(currentConditions.getWeather());
            }
            setBackgroundColor();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBackgroundColor() {
        String bgColor = "";
        // Loop over HashMap to find matches for background color
        for (Map.Entry<String, String> entry : Background.STATIC_MAP.entrySet()) {
            if (currentConditions != null && currentConditions.getIcon().matches(".*" + entry.getKey() + "*.")) {
                bgColor = entry.getValue();
                break;
            } else {
                bgColor = Background.DEFAULT;
            }
        }
        background.setBackgroundColor(Color.parseColor(bgColor));
    }

    public void displayHourlyForecast(JSONObject data) {
        try {
            // Remove data
            hourlyForecastArray.clear();
            hourlyTitleText.setVisibility(View.VISIBLE);
            hourlyForecastListView.setVisibility(View.VISIBLE);

            // Get hourly forecast array of objects
            JSONArray hourlyForecastJSONArray = data.getJSONArray("hourly_forecast");

            // Hourly forecast
            for (int i = 0; i < hourlyForecastJSONArray.length(); i++) {
                // Get JSON Objects
                JSONObject row = hourlyForecastJSONArray.getJSONObject(i);
                JSONObject timeinfo = row.getJSONObject("FCTTIME");
                JSONObject temp = row.getJSONObject("temp");

                // Get necessary values for hourly forecast
                String weekday_abbr = timeinfo.getString("weekday_name_abbrev");
                String time = timeinfo.getString("civil");
                String condition = row.getString("icon");
                int temp_english = temp.getInt("english");
                int temp_metric = temp.getInt("metric");

                HourlyForecast hourlyForecast = new HourlyForecast(weekday_abbr, time, condition, temp_english, temp_metric);
                hourlyForecastArray.add(hourlyForecast);
            }

            // Update listview array adapter
            hourlyAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void displayDailyForecast(JSONObject data) {
        try {
            // Remove data
            dailyForecastArray.clear();
            dailyTitleText.setVisibility(View.VISIBLE);
            dailyForecastListView.setVisibility(View.VISIBLE);

            // Get forecastday array of objects
            JSONObject forecast = data.getJSONObject("forecast");
            JSONObject simpleforecast = forecast.getJSONObject("simpleforecast");
            JSONArray forecastday = simpleforecast.getJSONArray("forecastday");

            // 7 day forecast
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

    @Override
    public void onConnected(Bundle bundle) {
        // Location client is connected
        isConnected = true;
    }

    @Override
    public void onDisconnected() {
        // Location client is disconnected
        isConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Failure to connect
        isConnected = false;
    }
}
