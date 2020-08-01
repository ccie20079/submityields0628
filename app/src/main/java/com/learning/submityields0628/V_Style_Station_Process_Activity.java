package com.learning.submityields0628;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.learning.Adapter.CommRVAdapter;
import com.learning.Adapter.HeaderRVAdapter;
import com.learning.Adapter.ShowGroupWithRV;
import com.learning.gson.V_Style_Station_Process;
import com.learning.utils.BaseActivity;
import com.learning.utils.DividerItemDecoration;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class V_Style_Station_Process_Activity extends BaseActivity {
    private String line_name;
    private String style_name;
    TextView tv_line_name;
    TextView tv_style_name;
    TextView tv_backToDrawerLayout;
    List<V_Style_Station_Process> v_style_station_processList;
    RecyclerView recyclerview_v_style_station_process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_style__distribution__detial);
        this.line_name  = getIntent().getStringExtra("line_name");
        this.style_name = getIntent().getStringExtra("style_name");
        tv_line_name = (TextView)findViewById(R.id.tv_line_name);
        tv_style_name = (TextView)findViewById(R.id.tv_style_name);
        tv_backToDrawerLayout = (TextView)findViewById(R.id.tv_backToDrawerLayout);
        tv_line_name.setText(line_name);
        tv_style_name.setText(style_name);
        tv_backToDrawerLayout.setOnClickListener(new tv_backToDrawerLayout_OnClickListenerImpl());
        recyclerview_v_style_station_process = (RecyclerView) findViewById(R.id.recyclerview_v_style_station_process);
        recyclerview_v_style_station_process.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_v_style_station_process.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        getTheProcessesByLineAndStyle(line_name,style_name);

    }

    private void getTheProcessesByLineAndStyle(String line_name, String style_name) {
        Map<String,String> map = new HashMap<>();
        map.put("line_name",line_name);
        map.put("style_name",style_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetProcessesByLineAndStyleNameAction), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                final List<V_Style_Station_Process> v_style_station_processList= (List<V_Style_Station_Process>) Utility.getListOfT(responseData,V_Style_Station_Process[].class);
                if(v_style_station_processList.size()==0) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        V_Style_Station_RV_Adapter v_style_station_rvAdapter = new V_Style_Station_RV_Adapter(v_style_station_processList);
//                        recyclerview_v_style_station_process.setAdapter(v_style_station_rvAdapter);
//                        CommRVAdapter commRVAdapter = new CommRVAdapter(v_style_station_processList);
//                        recyclerview_v_style_station_process.setAdapter(commRVAdapter);
                        ShowGroupWithRV.showGroupWithRV(recyclerview_v_style_station_process,v_style_station_processList);
                    }
                });

            }
        });
    }

    private class tv_backToDrawerLayout_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MyApplication.getContext(),V_Style_Line_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}