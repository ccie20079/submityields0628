package com.learning.utils;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/16
 * email:          ccie20079@126.com
 */
public class UriHelper {
    public static Uri getUriOfFile(File file,String authority){
        if(Build.VERSION.SDK_INT>=24){
            return FileProvider.getUriForFile(MyApplication.getContext(),authority,file);
        }else{
            return Uri.fromFile(file);
        }
    }

}
