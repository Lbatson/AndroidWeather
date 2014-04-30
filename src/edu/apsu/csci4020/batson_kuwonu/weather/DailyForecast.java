package edu.apsu.csci4020.batson_kuwonu.weather;


public class DailyForecast {

    private String weekday;
    private Integer day;
    private String conditions;
    private Integer high_f;
    private Integer low_f;
    private Integer high_c;
    private Integer low_c;

    public DailyForecast() {
    }

    public DailyForecast(String weekday, Integer day, String conditions, Integer high_f, Integer low_f, Integer high_c, Integer low_c) {
        this.weekday = weekday;
        this.day = day;
        this.conditions = conditions;
        this.high_f = high_f;
        this.high_c = high_c;
        this.low_f = low_f;
        this.low_c = low_c;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getCondition() {
        return conditions;
    }

    public void setCondition(String condition) {
        this.conditions = condition;
    }

    public Integer getHigh_f() {
        return high_f;
    }

    public void setHigh_f(Integer high_f) {
        this.high_f = high_f;
    }

    public Integer getHigh_c() {
        return high_c;
    }

    public void setHigh_c(Integer high_c) {
        this.high_c = high_c;
    }

    public Integer getLow_f() {
        return low_f;
    }

    public void setLow_f(Integer low_f) {
        this.low_f = low_f;
    }

    public Integer getLow_c() {
        return low_c;
    }

    public void setLow_c(Integer low_c) {
        this.low_c = low_c;
    }

    @Override
    public String toString() {
        return "DailyForecast{" +
                "weekday='" + weekday + '\'' +
                ", day=" + day +
                ", high_f=" + high_f +
                ", low_f=" + low_f +
                ", high_c=" + high_c +
                ", low_c=" + low_c +
                '}';
    }
}
