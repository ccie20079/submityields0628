package com.learning.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/29
 * email:          ccie20079@126.com
 */
public class WPSHelper {
    public static boolean openFile(String path)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("OPEN_MODE", "READ_ONLY");
        bundle.putBoolean("SEND_CLOSE_BROAD", true);
        bundle.putString("THIRD_PACKAGE", "cn.wps.moffice_eng");
        bundle.putBoolean("CLEAR_BUFFER", true);
        bundle.putBoolean("CLEAR_TRACE", true);
        //bundle.putBoolean(CLEAR_FILE, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");

        File file = new File(path);
        if (file == null || !file.exists())
        {
            return false;
        }

        Uri contentUri = FileProvider.getUriForFile(MyApplication.getContext(),"com.learning.submityields0628.fileprovider",file);

        intent.setData(contentUri);
        intent.putExtras(bundle);
        try
        {
            MyApplication.getContext().startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
