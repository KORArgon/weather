package com.argon.weather.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getSimpleDateUtil(String var){
        SimpleDateFormat sdf = new SimpleDateFormat(var);
        return sdf.format(new Date());
    }

}
