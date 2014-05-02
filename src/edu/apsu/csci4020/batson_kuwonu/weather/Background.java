package edu.apsu.csci4020.batson_kuwonu.weather;

import java.util.HashMap;
import java.util.Map;

public class Background {
    public static final String DEFAULT = "#ffffff";
    public static final Map<String, String> STATIC_MAP = new HashMap<String, String>();
    static {
        final Map<String, String> map = STATIC_MAP;
        map.put("clear" , "#ffd1cb76");
        map.put("cloudy", "#ff9eabb1");
        map.put("rain"  , "#ff5581cc");
        map.put("snow"  , "#ffbff5cd");
        map.put("storm" , "#ff867084");
        map.put("sunny" , "#ffd1cb76");
        map.put("windy" , "#ff98c4e1");
    }
}
