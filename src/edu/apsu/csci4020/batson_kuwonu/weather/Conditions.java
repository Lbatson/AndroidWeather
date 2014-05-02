package edu.apsu.csci4020.batson_kuwonu.weather;

public class Conditions {

    private String weather;
    private int temp_f;
    private int temp_c;
    private String icon;

    public Conditions() {
    }

    public Conditions(String weather, int temp_f, int temp_c, String icon) {
        this.weather = weather;
        this.temp_f = temp_f;
        this.temp_c = temp_c;
        this.icon = icon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getTemp_f() {
        return temp_f;
    }

    public void setTemp_f(int temp_f) {
        this.temp_f = temp_f;
    }

    public int getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(int temp_c) {
        this.temp_c = temp_c;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Conditions{" +
                "weather='" + weather + '\'' +
                ", temp_f=" + temp_f +
                ", temp_c=" + temp_c +
                ", icon='" + icon + '\'' +
                '}';
    }
}
