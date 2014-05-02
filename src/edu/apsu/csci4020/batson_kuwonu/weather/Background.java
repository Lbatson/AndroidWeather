package edu.apsu.csci4020.batson_kuwonu.weather;

import java.util.HashMap;
import java.util.Map;

public class Background {
    public static final Map<String, String> staticMap = new HashMap<String, String>();
    static {
        final Map<String, String> map = staticMap;
        map.put("cloudy", "#ff9eabb1");
        map.put("rain", "#ff5581cc");
        map.put("snow", "#ffbff5cd");
        map.put("storm", "#ff867084");
        map.put("sunny", "#ffddd887");
        map.put("windy", "#ff98c4e1");
    }
}
