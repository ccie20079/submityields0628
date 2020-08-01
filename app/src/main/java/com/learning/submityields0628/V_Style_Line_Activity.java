package com.learning.submityields0628;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.learning.Adapter.V_Style_Line_Adapter;
import com.learning.gson.V_GetAllStylesDistribution;

import com.learning.utils.BaseActivity;
import com.learning.utils.DividerItemDecoration;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Package_name:   com.learning.submityields0628
 * user:           Administrator
 * date:           2020/7/7
 * email:          ccie20079@126.com
        */

/**
 * activity 要 public !!!!
 */
public class V_Style_Line_Activity extends BaseActivity {
    List<V_GetAllStylesDistribution> v_getAllStylesDistributionList;
    V_Style_Line_Adapter adapter;
    RecyclerView recyclerview_of_style_distribution;
    private SwipeRefreshLayout swipe_refresh;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_processdistribution_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //使Toolbar的功能和ActionBar一致
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        recyclerview_of_style_distribution = (RecyclerView)findViewById(R.id.recyclerview_of_style_distribution);
        //决定使用线性布局，每行显示一个子项。    默认排列为: vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview_of_style_distribution.setLayoutManager(layoutManager);
        //添加分割线
        //recyclerview_of_style_distribution.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        //加载所有款式的工序分布数据。
        swipe_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.skyblue);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAllStylesDistribution();
            }
        });
        loadAllStylesDistributionData();
    }

    private void refreshAllStylesDistribution() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllStylesDistributionAction), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                v_getAllStylesDistributionList =Utility.getListOfT(responseData,V_GetAllStylesDistribution[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipe_refresh.setRefreshing(false); //隐藏刷新进条.
                    }
                });
            }
        });
    }

    private void loadAllStylesDistributionData() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllStylesDistributionAction), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                v_getAllStylesDistributionList = Utility.getListOfT(responseData,V_GetAllStylesDistribution[].class);
                LogUtil.d(getTheTAGOfTheCurrActivity(),v_getAllStylesDistributionList.toString());
                //更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       adapter = new V_Style_Line_Adapter(v_getAllStylesDistributionList);
                       recyclerview_of_style_distribution.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private class tv_backToDrawerLayout_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(V_Style_Line_Activity.this,MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent =new Intent(V_Style_Line_Activity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

}
