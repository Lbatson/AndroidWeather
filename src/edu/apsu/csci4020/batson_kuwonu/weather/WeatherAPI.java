package edu.apsu.csci4020.batson_kuwonu.weather;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class WeatherAPI {

    private static final String API_URL =
        "http://api.wunderground.com/api/44e104dc3950125e/" +
        "conditions/forecast10day/hourly/q/";
    private MainActivity context;
    private JSONObject responseValues;

    public WeatherAPI(MainActivity context) {
        this.context = context;
    }

    public void retrieveWeatherInfo(String location) {
        // Request weather info on separate thread
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(location);
    }

    public class DownloadTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(API_URL + params[0] + ".json");

            try {
                // Execute HTTP Get Request
                HttpResponse response = client.execute(get);
                // Store response into JSON object
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseString = EntityUtils.toString(entity);
                    responseValues = new JSONObject(responseString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            context.mainTemperatureText.setText(responseValues.toString());
        }
    }
}
