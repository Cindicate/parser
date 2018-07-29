package ru.job.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import static ru.job.parser.Params.*;

public class Converter {
    public static String convertToDate(byte[] valueBuf) {
        SimpleDateFormat sdf = new SimpleDateFormat (getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date d = new Date(convertByteToInt(valueBuf)*1000L);
        String s = sdf.format(d);
        return s;
    }

    public static int convertByteToInt(byte[] b){
        int value= 0;
        for(int i=0;i<b.length;i++){
            int n=(b[i]<0?(int)b[i]+256:(int)b[i])<<(8*i);
            value+=n;
        }
        return value;
    }

    public static BigDecimal convertByteToFVLN(byte[] b){
        byte[] scale = Arrays.copyOfRange(b, 0, 1);
        byte[] value = Arrays.copyOfRange(b, 1, b.length);
        BigDecimal bigDecimal = new BigDecimal(new BigInteger(value),convertByteToInt(scale));
        return bigDecimal;
    }

    public static String tagToName(Integer tag) {
        String tagName = (String) getTagNameMap().get(tag);
        return tagName!=null ? tagName : String.valueOf(tag);
    }

    public static Integer calcLength(Integer tag, Integer length){
        return getMaxLength().containsKey(tag) && length>(Integer) getMaxLength().get(tag)
                ? (Integer) getMaxLength().get(tag)
                : length;
    }
}
