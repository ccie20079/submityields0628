package com.learning.submityields0628;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.gson.Emp_Info;
import com.learning.gson.V_Team_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.Utility;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddEmpActivity extends BaseActivity {
    TextView tv_backToDrawerLayout;
    TextView tv_teamName;
    Spinner spinner_teamName;
    EditText ed_empName;
    TextView tv_IMEI;
    FloatingActionButton floatingActionButton_addEmpInfo;
    TextView tv_result;
    Map<String,String> map  = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        tv_backToDrawerLayout = (TextView)findViewById(R.id.tv_backToDrawerLayout);
        tv_backToDrawerLayout.setOnClickListener(new tv_backToDrawerLayout_OnClickListenerImpl());
        tv_teamName = (TextView)findViewById(R.id.tv_teamName);
        spinner_teamName=(Spinner)findViewById(R.id.spinner_teamName);
        ed_empName =(EditText)findViewById(R.id. ed_empName);
        tv_IMEI =( TextView)findViewById(R.id.tv_IMEI);
        floatingActionButton_addEmpInfo=(FloatingActionButton)findViewById(R.id.floatingActionButton_addEmpInfo);
        tv_result=(TextView)findViewById(R.id.tv_result);
        floatingActionButton_addEmpInfo.setOnClickListener(new floatingActionButton_addEmpInfo_OnClickListenerImpl());

        getIMEI();
        //获取所有的组
        getAllTeamInfo();

    }
    private void getAllTeamInfo() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllTeamInfos), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = response.body().string();
                List<V_Team_Info> v_team_infoList = Utility.getAllTeamsInfoOrderByCreatedTime(responseDate);
                final ArrayAdapter<V_Team_Info> arrayAdapter = new ArrayAdapter<V_Team_Info>(MyApplication.getContext(),android.R.layout.simple_spinner_dropdown_item,v_team_infoList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner_teamName.setAdapter(arrayAdapter);
                    }
                });
            }
        });

    }
    private void getIMEI() {
        final TelephonyManager tm = (TelephonyManager) this.getSystemService(getApplication().TELEPHONY_SERVICE);
        PermissionX.init(this).permissions(Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_PHONE_STATE).request(new RequestCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (allGranted) {
                    final String Imei = tm.getImei(0);
                    tv_IMEI.setText(Imei);
                    //判断此IMEI是否已经注册：
                    map.put("imei",Imei);
                    judgeIfExistsIMEI(map);
                    return;
                }else{
                    Toast.makeText(MyApplication.getContext(),"您拒绝了权限的获取,将可能无法获取您的IMEI.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    private void judgeIfExistsIMEI(Map<String, String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpInfoByIMEI), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                final Emp_Info v_emp_info;

                v_emp_info= Utility.getEmpInfo(responseData);
                if(v_emp_info!=null){
                    //已经存在该用户。
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddEmpActivity.this.ed_empName.setEnabled(false);
                            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(AddEmpActivity.this);
                            dialog.setTitle("此IMEI已注册:" );
                            dialog.setCancelable(false);
                            dialog.setItems(new String[]{v_emp_info.toString()}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                            //dialog.setView(R.layout.alert_dialog_background);
                            dialog.show();
                        }
                    });
                }
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
    private class floatingActionButton_addEmpInfo_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if("".equals(tv_teamName.getText().toString())){
                return;
            }
            if("".equals(ed_empName.getText().toString())) return;
            map.put("emp_name",ed_empName.getText().toString());
            //判断有无同名用户？
            judgeIfExistsSameEmpName(map );

        }
    }
    private void judgeIfExistsSameEmpName(final Map<String,String> map ) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpInfoAction), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = null;
                responseDate = response.body().string();
                final Emp_Info v_emp_info = Utility.getEmpInfo(responseDate);
                if(null!=v_emp_info){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddEmpActivity.this.ed_empName.setEnabled(false);
                            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(AddEmpActivity.this);
                            dialog.setTitle("此姓名已注册:" );
                            dialog.setCancelable(false);
                            dialog.setItems(new String[]{v_emp_info.toString()}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                            //dialog.setView(R.layout.alert_dialog_background);
                            dialog.show();
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
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseDate = response.body().string();
                List<Emp_Info> emp_infoList = Utility.getEmpInfos(responseDate);
                if(0!=emp_infoList.size()){
                    //已经存在该用户。
                    final CharSequence[] charSequences = emp_infoList.toArray(new CharSequence[emp_infoList.size()]);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(AddEmpActivity.this);
                            dialog.setTitle("存在拼音相同的用户:" );
                            dialog.setCancelable(false);
                            dialog.setItems(charSequences, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveEmpInfo(map);
                                    return;
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                            //dialog.setView(R.layout.alert_dialog_background);
                            dialog.show();
                        }
                    });
                    return;
                }
                V_Team_Info v_team_info = (V_Team_Info)spinner_teamName.getSelectedItem();
                map.put("team_name",v_team_info.getTeam_name());
                map.put("monitor",v_team_info.getMonitor());
                saveEmpInfo(map);
            }
        });
    }
    private void saveEmpInfo(Map<String,String> map){
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfSaveEmpInfo), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
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
                        tv_result.setText(empInfo.toString());
                    }
                });
            }
        });
    }
}