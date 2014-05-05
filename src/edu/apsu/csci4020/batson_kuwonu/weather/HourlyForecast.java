package edu.apsu.csci4020.batson_kuwonu.weather;

public class HourlyForecast {

    private String weekday_name_abbrev;
    private String time;
    private String condition;
    private Integer temp_english;
    private Integer temp_metric;

    public HourlyForecast() {
    }

    public HourlyForecast(String weekday_name_abbrev, String time, String condition, Integer temp_english, Integer temp_metric) {
        this.weekday_name_abbrev = weekday_name_abbrev;
        this.time = time;
        this.condition = condition;
        this.temp_english = temp_english;
        this.temp_metric = temp_metric;
    }

    public String getWeekday_name_abbrev() {
        return weekday_name_abbrev;
    }

    public void setWeekday_name_abbrev(String weekday_name_abbrev) {
        this.weekday_name_abbrev = weekday_name_abbrev;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTemp_english() {
        return temp_english;
    }

    public void setTemp_english(Integer temp_english) {
        this.temp_english = temp_english;
    }

    public int getTemp_metric() {
        return temp_metric;
    }

    public void setTemp_metric(Integer temp_metric) {
        this.temp_metric = temp_metric;
    }

    @Override
    public String toString() {
        return "HourlyForecast{" +
                "weekday_name_abbrev='" + weekday_name_abbrev + '\'' +
                ", time='" + time + '\'' +
                ", condition='" + condition + '\'' +
                ", temp_english=" + temp_english +
                ", temp_metric=" + temp_metric +
                '}';
    }
}
