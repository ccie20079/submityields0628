package com.learning.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/27 0027
 * email:          ccie20079@126.com
 */
public class DateHelper {
    public static String getTodayStr(){
        String result = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return  result;
    }
    public static String getCurrMonthStr(){
        String result = new SimpleDateFormat("yyyy-MM").format(new Date());
        return  result;
    }
}
