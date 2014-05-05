package edu.apsu.csci4020.batson_kuwonu.weather;

import java.util.HashMap;
import java.util.Map;

public class Images {
    public static final int DEFAULT = R.drawable.sunny;
    public static final Map<String, Integer> STATIC_MAP = new HashMap<String, Integer>();
    static {
        final Map<String, Integer> map = STATIC_MAP;
        map.put("clear" , R.drawable.sunny);
        map.put("cloudy", R.drawable.cloudy);
        map.put("rain"  , R.drawable.rainy);
        map.put("snow"  , R.drawable.snowy);
        map.put("storm" , R.drawable.stormy);
        map.put("sunny" , R.drawable.sunny);
        map.put("windy" , R.drawable.windy);
    }
}
