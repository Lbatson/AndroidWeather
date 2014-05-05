package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HourlyAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<HourlyForecast> hourlyForecastArrayAdapter;

    public HourlyAdapter(Context context, ArrayList<HourlyForecast> hourlyForecastArrayAdapter) {
        this.context = context;
        this.hourlyForecastArrayAdapter = hourlyForecastArrayAdapter;
    }

    @Override
    public int getCount() {
        return hourlyForecastArrayAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return hourlyForecastArrayAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return hourlyForecastArrayAdapter.indexOf(getItem(position));
    }

    static class ViewHolder {
        TextView weekday_name_abbrev;
        TextView time;
        TextView condition;
        TextView temp_english;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Create or get view
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.hourly_list, null);
            holder = new ViewHolder();
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Access the view elements
        holder.weekday_name_abbrev = (TextView) convertView.findViewById(R.id.tv_hourly_weekday_abbr);
        holder.time = (TextView) convertView.findViewById(R.id.tv_hourly_time);
        holder.condition = (TextView) convertView.findViewById(R.id.tv_hourly_condition);
        holder.temp_english = (TextView) convertView.findViewById(R.id.tv_hourly_temp);

        // Set view values
        HourlyForecast forecast = hourlyForecastArrayAdapter.get(position);
        holder.weekday_name_abbrev.setText(forecast.getWeekday_name_abbrev());
        holder.time.setText(forecast.getTime());
        holder.condition.setText(forecast.getCondition());
        holder.temp_english.setText(String.valueOf(forecast.getTemp_english()));

        // Insure the item remains in the proper spot in the list
        convertView.setTag(holder);
        return convertView;
    }
}
