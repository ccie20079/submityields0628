package com.learning.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/11
 * email:          ccie20079@126.com
 */
public class ActivityHelper {
    /**
     * 通过 context获取Activity
     * @param context
     * @return
     */
    public static Activity getActivityByContext(Context context){
        if(Activity.class.isInstance(context)){
            Activity activity = (Activity)context;
            return activity;
        }
        return null;
    }
}
