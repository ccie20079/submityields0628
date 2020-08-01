package com.learning.utils;

import java.text.DecimalFormat;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/10
 * email:          ccie20079@126.com
 */
public class DecimalHelper {
    /**
     * 获取两位小数
     * @param d
     * @return
     */
    public static String getTwoDecimalPlaces(double d){
        DecimalFormat df   = new DecimalFormat("#######.00");
        return df.format(d);
    }
    public static String getTwoDecimalPlaces2(double d){
        return String.format(".2d",d);
    }
    public static double getTwoDecimalPlacesReturnedDouble(double d){
        return Math.round(100*d)/100.00;
    }
}
