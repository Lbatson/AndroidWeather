package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    public TextView mainTemperatureText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mainTemperatureText = (TextView) findViewById(R.id.mainTemperatureText);

        WeatherAPI weatherAPI = new WeatherAPI(this);
        weatherAPI.retrieveWeatherInfo("37043");
    }
}
