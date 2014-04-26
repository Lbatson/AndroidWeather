package edu.apsu.csci4020.batson_kuwonu.weather;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI {

    public interface WeatherAPIListener {
        public void onWeatherInfoRetrieved(JSONObject responseData, boolean error);
    }

    private WeatherAPIListener mListener;
    private String API_URL;
    private JSONObject responseValues;

    public WeatherAPI(MainActivity context) {
        mListener = (WeatherAPIListener) context;
        API_URL = context.getResources().getString(R.string.api_url);
    }

    public DownloadTask retrieveWeatherInfo(String location) {
        // Request weather info on separate thread
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(location);
        return downloadTask;
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
            // Get response object and check for error
            // Send response or error json object back to listener
            try {
                JSONObject checker = responseValues.getJSONObject("response");
                if (checker.has("error")) {
                    JSONObject responseErr = checker.getJSONObject("error");
                    mListener.onWeatherInfoRetrieved(responseErr, true);
                }
                else {
                    mListener.onWeatherInfoRetrieved(responseValues, false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
