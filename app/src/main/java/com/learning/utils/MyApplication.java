package com.learning.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityCompat;

import com.learning.gson.Emp_Info;
/*
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/26 0026
 * email:          ccie20079@126.com
 */
public class MyApplication extends Application {
    private static Context context;
    public static String MAC;
    /**
     * 本机注册的信息
     */
    private static Emp_Info emp_info;

    public static Emp_Info getEmp_info() {
        return emp_info;
    }

    public static void setEmp_info(Emp_Info emp_info) {
        MyApplication.emp_info = emp_info;
    }

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
    public static String getActivityName(){
        if(Activity.class.isInstance(getContext())){
            return ((Activity)context).getClass().getSimpleName();
        }
        return "当前上下文： 不是一个活动！";
    }

}