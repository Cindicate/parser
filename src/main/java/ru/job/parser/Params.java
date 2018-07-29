package ru.job.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static ru.job.parser.Utils.createMap;

public class Params {
    private static int tagLength = 2;
    private static int lenLength = 2;

    private static String charset = "CP866";
    private static String timeZone = "UTC";
    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static HashMap<Object, Object> tagNameMap = createMap(
            1,"dateTime",
            2,"orderNumber",
            3,"customerName",
            4, "items",
            11, "name",
            12, "price",
            13, "quantity",
            14, "sum"
    );

    public static Set<Integer> tagEndTLV = new HashSet<>(Arrays.asList(4,14));

    public static Set<Integer> dataTypeUnit32 = new HashSet<>(Arrays.asList(1));
    public static Set<Integer> dataTypeString = new HashSet<>(Arrays.asList(3,11));
    public static Set<Integer> dataTypeVLN = new HashSet<>(Arrays.asList(2,12,14));
    public static Set<Integer> dataTypeFVLN = new HashSet<>(Arrays.asList(13));
    public static Set<Integer> dataTypeTLV = new HashSet<>(Arrays.asList(4));

    public static HashMap<Object, Object> maxLength;

    public static HashMap<Object, Object> getMaxLength() {
        if(maxLength==null) maxLength = createMap(2,8,3,1000,11,200,12,6,13,8,14,6);
        return maxLength;
    }

    public static int getTagLength() {
        return tagLength;
    }

    public static int getLenLength() {
        return lenLength;
    }

    public static String getCharset() {
        return charset;
    }

    public static String getTimeZone() {
        return timeZone;
    }

    public static String getDateFormat() {
        return dateFormat;
    }

    public static HashMap<Object, Object> getTagNameMap() {
        return tagNameMap;
    }

    public static Set<Integer> getTagEndTLV() {
        return tagEndTLV;
    }
}
