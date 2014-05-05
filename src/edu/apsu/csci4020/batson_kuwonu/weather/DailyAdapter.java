package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class DailyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DailyForecast> dailyForecastArrayList;

    public DailyAdapter(Context context, ArrayList<DailyForecast> dailyForecastArrayList) {
        this.context = context;
        this.dailyForecastArrayList = dailyForecastArrayList;
    }

    @Override
    public int getCount() {
        return dailyForecastArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyForecastArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dailyForecastArrayList.indexOf(getItem(position));
    }

    static class ViewHolder {
        TextView weekday;
        TextView day;
        ImageView condition;
        TextView high;
        TextView low;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Create or get view
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.daily_list, null);
            holder = new ViewHolder();
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Access the view elements
        holder.weekday = (TextView) convertView.findViewById(R.id.tv_daily_weekday);
        holder.day = (TextView) convertView.findViewById(R.id.tv_daily_day);
        holder.condition = (ImageView) convertView.findViewById(R.id.iv_daily_conditions);
        holder.high = (TextView) convertView.findViewById(R.id.tv_daily_high);
        holder.low = (TextView) convertView.findViewById(R.id.tv_daily_low);

        // Set view values
        DailyForecast forecast = dailyForecastArrayList.get(position);
        holder.weekday.setText(forecast.getWeekday());
        holder.day.setText(forecast.getDay().toString());
        holder.high.setText(forecast.getHigh_f().toString());
        holder.low.setText(forecast.getLow_f().toString());

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
