package com.learning.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/27 0027
 * email:          ccie20079@126.com
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        LogUtil.d(TAG,getClass().getSimpleName());      //查看当前是哪个活动
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    public String getTheTAGOfTheCurrentInstance(){
        return getClass().getSimpleName();
    }
}
