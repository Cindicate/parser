package ru.job.parser;

import java.util.Arrays;
import java.util.HashMap;

public class Utils {
    public static HashMap<Object, Object> createMap(Object... params) {
        if(params.length % 2 != 0) {
            throw new IllegalArgumentException(Arrays.toString(params));
        } else {
            HashMap map = new HashMap();

            for(int i = 0; i < params.length; i += 2) {
                map.put(params[i], params[i + 1]);
            }

            return map;
        }
    }

}
