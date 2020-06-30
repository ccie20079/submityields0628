package com.learning.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/27 0027
 * email:          ccie20079@126.com
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
