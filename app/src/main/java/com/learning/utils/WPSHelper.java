package com.learning.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

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

    private static Intent getWordFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        if(type.contains("pdf") || type.contains("vnd.ms-powerpoint") || type.contains("vnd.ms-word") || type.contains("vnd.ms-excel") || type.contains("text/plain")|| type.contains("text/html")){
            if (isInstall( "cn.wps.moffice_eng")) {
                intent.setClassName("cn.wps.moffice_eng",
                        "cn.wps.moffice.documentmanager.PreStartActivity2");
                intent.setData(uri);
            } else {
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(uri, type);
            }
        }else{
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(uri, type);
        }
        return intent;
    }
    public static Intent getWordFileIntent(File file,Uri uri) {
        //File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        if(type.contains("pdf") || type.contains("vnd.ms-powerpoint") || type.contains("vnd.ms-word") || type.contains("vnd.ms-excel") || type.contains("text/plain")|| type.contains("text/html")){
            if (isInstall( "cn.wps.moffice_eng")) {
                intent.setClassName("cn.wps.moffice_eng",
                        "cn.wps.moffice.documentmanager.PreStartActivity2");
                intent.setData(uri);
            } else {
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(uri, type);
            }
        }else{
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(uri, type);
        }
        return intent;
    }
    private static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else if (end.equals("pptx") || end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("docx") || end.equals("doc")) {
            type = "application/vnd.ms-word";
        } else if (end.equals("xlsx") || end.equals("xls")) {
            type = "application/vnd.ms-excel";
        }else if(end.equals("txt")){
            type = "text/plain";
        }else if(end.equals("html") || end.equals("htm")){
            type = "text/html";
        } else {
            //如果无法直接打开，就跳出软件列表给用户选择

        } type = "*/*";
        return type;
    }
    private static boolean isInstall( String packageName) {
        final PackageManager packageManager = MyApplication.getContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


}
