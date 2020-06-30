package com.learning.utils;

import android.net.Uri;
import android.os.Build;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/11
 * email:          ccie20079@126.com
 */
public class SoftInputHelper {
    public static void setShowSoftInputOnFocus(EditText ed,boolean flag){
//        if(Build.VERSION.SDK_INT>=21){
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        if(Build.VERSION.SDK_INT>=21){
            ed.setShowSoftInputOnFocus(false);
        }
    }
    public static void disableShowInput(EditText et) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(et, false);
        } catch (Exception e) {//TODO: handle exception
        }
    }
}
