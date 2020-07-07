package com.learning.submityields0628;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.learning.Adapter.V_Style_Line_Adapter;
import com.learning.gson.V_GetAllStylesDistribution;

import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import org.jetbrains.annotations.NotNull;

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
    RecyclerView recyclerview_of_style_distribution;
    List<V_GetAllStylesDistribution> v_getAllStylesDistributionList;
    TextView tv_backToDrawerLayout;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_processdistribution_layout);
        tv_backToDrawerLayout = (TextView)findViewById(R.id.tv_backToDrawerLayout);
        tv_backToDrawerLayout.setOnClickListener(new tv_backToDrawerLayout_OnClickListenerImpl());
        recyclerview_of_style_distribution = (RecyclerView)findViewById(R.id.recyclerview_of_style_distribution);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview_of_style_distribution.setLayoutManager(layoutManager);
        //加载所有款式的工序分布数据。
        loadAllStylesDistributionData();
    }

    private void loadAllStylesDistributionData() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllStylesDistributionAction), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                v_getAllStylesDistributionList = Utility.getAllStylesDistribution(responseData);

                LogUtil.d(getTheTAGOfTheCurrentInstance(),v_getAllStylesDistributionList.toString());
                //更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        V_Style_Line_Adapter adapter = new V_Style_Line_Adapter(v_getAllStylesDistributionList);
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
            finish();
        }
    }
}
