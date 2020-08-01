package com.learning.submityields0628;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.gson.Emp_Info;
import com.learning.gson.V_Team_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.ShowAlertDialog;
import com.learning.utils.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddEmpActivity extends BaseActivity {
    TextView tv_backToDrawerLayout;
    Spinner spinner_teamName;
    EditText ed_empName;
    TextView tv_mac;
    FloatingActionButton fabtn_addEmpInfo;
    TextView tv_result;
    Map<String,String> map  = new HashMap<>();
    List<V_Team_Info> v_team_infoList;
    ArrayAdapter<V_Team_Info> arrayAdapter_v_team_info = null;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        tv_backToDrawerLayout = (TextView)findViewById(R.id.tv_backToDrawerLayout);
        tv_backToDrawerLayout.setOnClickListener(new tv_backToDrawerLayout_OnClickListenerImpl());
        spinner_teamName=(Spinner) findViewById(R.id.spinner_teamName);

        ed_empName =(EditText)findViewById(R.id. ed_empName);
        tv_mac =( TextView)findViewById(R.id.tv_mac);

        fabtn_addEmpInfo=(FloatingActionButton)findViewById(R.id.fabtn_addEmpInfo);
        tv_result=(TextView)findViewById(R.id.tv_result);
        fabtn_addEmpInfo.setOnClickListener(new fabtn_addEmpInfo_OnClickListenerImpl());

        spinner_teamName.setOnItemSelectedListener(new spinner_teamName_OnItemSelectedListenerImpl());

        if(MyApplication.getEmp_info()!=null){
            tv_result.setText(MyApplication.getEmp_info().toString());
            ed_empName.setEnabled(false);
            fabtn_addEmpInfo.setEnabled(false);
        }else{
            tv_result.setText("");
            ed_empName.setEnabled(true);
            fabtn_addEmpInfo.setEnabled(true);
        }
        tv_mac.setText(MyApplication.MAC);
        //获取所有的组
        getAllTeamInfo();

    }
    private void getAllTeamInfo() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllTeamInfos), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = response.body().string();
                v_team_infoList= Utility.getListOfT(responseDate,V_Team_Info[].class);
                v_team_infoList.add(0,null);
                arrayAdapter_v_team_info = new ArrayAdapter<>(MyApplication.getContext(),R.layout.spinner_item,v_team_infoList);
                arrayAdapter_v_team_info.setDropDownViewResource(R.layout.spinner_dropdown_style);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner_teamName.setAdapter(arrayAdapter_v_team_info);
                        spinner_teamName.setSelection(0,true);
                    }
                });

            }
        });
    }

    private class tv_backToDrawerLayout_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent  = new Intent(MyApplication.getContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     *
     */
    private class fabtn_addEmpInfo_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(MyApplication.getEmp_info()!=null) return;
            if(null==spinner_teamName.getSelectedItem()){
                return;
            }
            if("".equals(ed_empName.getText().toString())) return;
            map.put("emp_name",ed_empName.getText().toString());
            map.put("mac",tv_mac.getText().toString());
            //判断有无同名用户？
            judgeIfExistsSameEmpName(map);
        }
    }
    private void judgeIfExistsSameEmpName(final Map<String,String> map ) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpInfoAction), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                final Emp_Info emp_info = Utility.getT(responseData,Emp_Info.class);
                if(null!=emp_info){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddEmpActivity.this.ed_empName.setEnabled(false);
                            List<Emp_Info> list = new ArrayList();
                            list.add(emp_info);
                            ShowAlertDialog.show(AddEmpActivity.this,"此姓名已注册！", list,  "确定");
                        }
                    });
                    return;
                }
                //判断是否存在相似用户
                judgeIfExistsSamePinYinButWrite(map);
            }
        });
    }

    private void judgeIfExistsSamePinYinButWrite(final Map<String,String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetTheListOfSamePinYinButWrite), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                final List<Emp_Info> emp_infoList = Utility.getListOfT(responseData,Emp_Info[].class);
                if(0!=emp_infoList.size()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowAlertDialog.show(AddEmpActivity.this, "存在拼音相同的记录：",
                                    emp_infoList,
                                   "继续新增", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //获取已经选择的
                                            saveEmpInfo(map);
                                        }
                                    });
                        }
                    });
                    return;
                }
                saveEmpInfo(map);
            }
        });
    }
    private void saveEmpInfo(Map<String,String> map){
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfSaveEmpInfo), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData =  response.body().string();
                final Emp_Info empInfo = Utility.getEmpInfo(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null == responseData){
                            tv_result.setText("保存失败！");
                            return;
                        }
                        //保存成功！
                        tv_result.setText(empInfo.toString());
                        MyApplication.setEmp_info( empInfo);
                    }
                });
            }
        });
    }

    private class spinner_teamName_OnItemSelectedListenerImpl implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if(null==v_team_infoList.get(position))
                return;
            V_Team_Info v_team_info = (V_Team_Info)spinner_teamName.getSelectedItem();
            map.put("team_name",v_team_info.getTeam_name());
            map.put("monitor",v_team_info.getMonitor());
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}