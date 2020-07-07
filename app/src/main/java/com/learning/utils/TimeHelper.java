package com.learning.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.text.Regex;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/21
 * email:          ccie20079@126.com
 */
public class TimeHelper {
    public static String getCurrTimeStr(){
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
    }

}
