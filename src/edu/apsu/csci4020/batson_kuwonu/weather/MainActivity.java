package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements SearchDialogFragment.SearchDialogListener, WeatherAPI.WeatherAPIListener {

    private WeatherAPI weatherAPI;
    private WeatherAPI.DownloadTask downloadTask;

    public TextView mainTemperatureText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initial setup
        mainTemperatureText = (TextView) findViewById(R.id.mainTemperatureText);
        weatherAPI = new WeatherAPI(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop retrieving weather data on pause
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
        downloadTask = weatherAPI.retrieveWeatherInfo(zip_code_value);
    }

    @Override
    public void onWeatherInfoRetrieved(JSONObject responseData, boolean error) {
        try {
            if (error) {
                String err = responseData.getString("description");
                mainTemperatureText.setText("Error: " + err);
            } else {
                JSONObject current = responseData.getJSONObject("current_observation");
                mainTemperatureText.setText("Current temp in F: " + current.getDouble("temp_f"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
