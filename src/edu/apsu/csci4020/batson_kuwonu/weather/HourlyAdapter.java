package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

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
        ImageView condition;
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
        holder.condition = (ImageView) convertView.findViewById(R.id.iv_hourly_condition);
        holder.temp_english = (TextView) convertView.findViewById(R.id.tv_hourly_temp);

        // Set view values
        HourlyForecast forecast = hourlyForecastArrayAdapter.get(position);
        holder.weekday_name_abbrev.setText(forecast.getWeekday_name_abbrev());
        holder.time.setText(forecast.getTime());
        holder.temp_english.setText(String.valueOf(forecast.getTemp_english()));

        // Loop over HashMap to find matches for image
        int image = 0;
        for (Map.Entry<String, Integer> entry : Images.STATIC_MAP.entrySet()) {
            if (forecast.getCondition() != null && forecast.getCondition().matches(".*" + entry.getKey() + "*.")) {
                image = entry.getValue();
                break;
            } else {
                image = Images.DEFAULT;
            }
        }
        holder.condition.setImageDrawable(context.getResources().getDrawable(image));

        // Insure the item remains in the proper spot in the list
        convertView.setTag(holder);
        return convertView;
    }
}
