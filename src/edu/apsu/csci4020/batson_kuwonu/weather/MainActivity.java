package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    public TextView mainTemperatureText;
    private WeatherAPI weatherAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mainTemperatureText = (TextView) findViewById(R.id.mainTemperatureText);

        weatherAPI = new WeatherAPI(this);
//        weatherAPI.retrieveWeatherInfo("37043");
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
            case R.id.action_search:
                return true;
            case R.id.action_gps:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
