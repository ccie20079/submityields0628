package com.learning.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
/*
import org.litepal.LitePal;
import org.litepal.LitePalApplication;
*/
/**
 */
/*
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/26 0026
 * email:          ccie20079@126.com
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //LitePal.initialize(context);
        //效果和在AndroidManifest.xml中配置LitePalApplication是一模一样的
        //程序启动时，初始化 MyApplication类。
    }

    public static Context getContext() {
        return context;
    }
    public static Activity getActivity(Context context){
        if(Activity.class.isInstance(context)){
            return (Activity)context;
        }
        return null;
    }
}