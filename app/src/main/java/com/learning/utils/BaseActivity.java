package com.learning.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.learning.submityields0628.R;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/27 0027
 * email:          ccie20079@126.com
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG,getClass().getSimpleName()+": onCreate...");      //查看当前是哪个活动
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        LogUtil.d(TAG,getClass().getSimpleName()+": onDestroy...");
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        LogUtil.d(TAG,getClass().getSimpleName()+": onRestart...");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        LogUtil.d(TAG,getClass().getSimpleName()+": onStart...");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        LogUtil.d(TAG,getClass().getSimpleName()+": onResume...");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        LogUtil.d(TAG,getClass().getSimpleName()+": onResume...");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        LogUtil.d(TAG,getClass().getSimpleName()+": onPause...");
//    }

    public String getTheTAGOfTheCurrActivity(){
        return getClass().getSimpleName();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
