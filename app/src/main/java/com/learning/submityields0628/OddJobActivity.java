package com.learning.submityields0628;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.learning.Enum.Get_Similar_Odd_Job_Enum;
import com.learning.Enum.Odd_Job_Enum;
import com.learning.gson.V_GET_Similar_Odd_Job;
import com.learning.gson.V_Line_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 零活类
 */
public class OddJobActivity extends BaseActivity {
    private EditText editTextOfDatePicker;
    private EditText edOfParticulars;
    private EditText edOfManhours;
    private EditText edOfQuantities;
    private EditText edOfAmountOfMoney;
    private EditText edOfEmpName;
    private Button btnSubmitOddJob;
    private Button btn_back_to_MainActivity;

    private AppCompatSpinner appCompatSpinnerOfLineName;

    private String v_particulars = "";
    private String v_labor_hours_str = "";
    private String v_quantities_str = "";
    private String v_amount_of_money_str = "";
    private String v_emp_name = "";
    private String v_year_month_day_str = "";


    private static final int REQUEST_GET_Name_By_QRCode = 1;
    private boolean isSpinnerFirst  = true;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odd_job);
        editTextOfDatePicker = (EditText)findViewById(R.id.editTextOfDatePicker);
        edOfParticulars = (EditText)findViewById(R.id.edOfParticulars);
        edOfManhours = (EditText)findViewById(R.id.edOfManhours);
        edOfQuantities = (EditText)findViewById(R.id.edOfQuantities);
        edOfAmountOfMoney = (EditText)findViewById(R.id.edOfAmountOfMoney);
        appCompatSpinnerOfLineName = (AppCompatSpinner)findViewById(R.id.appCompatSpinnerOfLineName);
        edOfEmpName = (EditText)findViewById(R.id.edOfEmpName);
        btnSubmitOddJob=(Button)findViewById(R.id.btnSubmitOddJob);
        btn_back_to_MainActivity = (Button)findViewById(R.id.btn_back_to_MainActivity);

        //设置日期为当天
        editTextOfDatePicker.setText(DateHelper.getTodayStr());
        lostFocus();

        editTextOfDatePicker.setOnFocusChangeListener(new editTextOfDatePicker_onFocusChangeListenerImpl());
        editTextOfDatePicker.setOnTouchListener(new editTextOfDatePicker_onTouchListenerImpl());

        this.edOfEmpName.setOnClickListener(new edOfEmpName_OnClickListenerImpl());
        btnSubmitOddJob.setOnClickListener(new submitOddJob_OnClickListenerImpl());
        btn_back_to_MainActivity.setOnClickListener(new btn_back_to_MainActivity_onClickListenerImpl());
        //加载线体
        loadAllLineInfo();
        appCompatSpinnerOfLineName.setOnItemSelectedListener(new appCompatSpinnerOfLineName_OnItemSelectedListenerImpl());
    }

    /**
     * 加载所有线体
     */
    private void loadAllLineInfo() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllLineInfos), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                  final List<V_Line_Info> v_line_infoList     =Utility.getAllLinesInfoOrderByCreatedTime(responseData);
                  if(v_line_infoList.size()==0){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(OddJobActivity.this,"尚未创建任何线体，请先创建",Toast.LENGTH_LONG).show();
                              return;
                          }
                      });
                  }
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          ArrayAdapter<V_Line_Info> arrayAdapter = new ArrayAdapter<>(OddJobActivity.this,android.R.layout.simple_spinner_dropdown_item,v_line_infoList);
                          OddJobActivity.this.appCompatSpinnerOfLineName.setAdapter(arrayAdapter);
                          appCompatSpinnerOfLineName.setSelection(-1);
                      }
                  });

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void lostFocus(){
        this.edOfParticulars.requestFocus();
        //this.editTextOfLineName.setShowSoftInputOnFocus(false);
        this.edOfEmpName.setShowSoftInputOnFocus(false);

    }

    /**
     * 提交零活记录。
     */
    private class submitOddJob_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //检查
            //1.事由
            if("".equals(edOfParticulars.getText().toString())) {
                edOfParticulars.requestFocus();
                return;
            }
            //2.金额
            if("".equals(edOfAmountOfMoney.getText().toString())){
                edOfAmountOfMoney.requestFocus();
                return;
            }
            //3.姓名
            if("".equals(edOfEmpName.getText().toString())){
                edOfEmpName.requestFocus();
                return;
            }
            //线体或地点
            if("".equals(appCompatSpinnerOfLineName.getSelectedItem().toString())) {
                appCompatSpinnerOfLineName.requestFocus();
                return;
            }
            //先判断是否有相似数据.
            Map<String,String> getSimilarOddJobMap = new HashMap<String,String>();
            getSimilarOddJobMap.put(Get_Similar_Odd_Job_Enum.v_particulars.toString(),edOfParticulars.getText().toString());
            getSimilarOddJobMap.put(Get_Similar_Odd_Job_Enum.v_amount_of_money_str.toString(),edOfAmountOfMoney.getText().toString());
            getSimilarOddJobMap.put(Get_Similar_Odd_Job_Enum.v_emp_name.toString(),edOfEmpName.getText().toString());
            getSimilarOddJobMap.put(Get_Similar_Odd_Job_Enum.v_year_month_day_str.toString(),editTextOfDatePicker.getText().toString());
            HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetSimilarOddJobAction), getSimilarOddJobMap, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<V_GET_Similar_Odd_Job> v_get_similar_odd_jobList = Utility.getSimilarOddJobList(response.body().string());
                    if(v_get_similar_odd_jobList ==null){
                        saveOddJobAction();
                        return;
                    }
                    CharSequence[] v_get_similar_odd_job_charSequences = v_get_similar_odd_jobList.toArray(new CharSequence[v_get_similar_odd_jobList.size()]);
                    //提示有相似零活数据。
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(OddJobActivity.this);
                    dialog.setTitle(String.format("%s 存在相似的记录 %d条.",editTextOfDatePicker.getText().toString(),v_get_similar_odd_jobList.size()));
                    dialog.setCancelable(false);
                    dialog.setItems(v_get_similar_odd_job_charSequences, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("继续提交", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveOddJobAction();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.show();
                        }
                    });

                }
            });
        }
    }

    /**
     * 提交零活
     */
    private void saveOddJobAction()
    {
       //4.准备数据
        Map<String,String> map = new HashMap<String,String>();
        map.put(Odd_Job_Enum.v_particulars.toString(),edOfParticulars.getText().toString());
        map.put(Odd_Job_Enum.v_labor_hours_str.toString(),edOfManhours.getText().toString());
        map.put(Odd_Job_Enum.v_quantities_str.toString(),edOfQuantities.getText().toString());
        map.put(Odd_Job_Enum.v_amount_of_money_str.toString(),edOfAmountOfMoney.getText().toString());
        map.put(Odd_Job_Enum.v_emp_name.toString(),edOfEmpName.getText().toString());
        map.put(Odd_Job_Enum.v_year_month_day_str.toString(),editTextOfDatePicker.getText().toString());
        Object o = appCompatSpinnerOfLineName.getSelectedItem();
        V_Line_Info v_line_info = (V_Line_Info)o;
        map.put(Odd_Job_Enum.v_place.toString(),v_line_info.getLine_name());
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfSaveOddJobAction), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData= response.body().string();
                final Integer result = Integer.parseInt(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(edOfEmpName,"已提交："+result+"行。",Snackbar.LENGTH_SHORT).show();//
                        //金额，姓名清空,定位到金额
                        OddJobActivity.this.edOfAmountOfMoney.setText("");
                        OddJobActivity.this.edOfEmpName.setText("");
                        OddJobActivity.this.edOfAmountOfMoney.requestFocus();
                    }
                });
            }
        });
    }
    /**
     * 日期选择监听器
     */
    private class editTextOfDatePicker_onFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                Toast.makeText(OddJobActivity.this,"onFoucsChangeListener",Toast.LENGTH_SHORT).show();
                showDatePickerDialog();
            }
        }
    }
    private class editTextOfDatePicker_onTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(OddJobActivity.this,"onTouchListener",Toast.LENGTH_SHORT).show();
                showDatePickerDialog();
                return true;
            }
            return true;
        }
    }
    /**
     * 显示日期选择框
     */
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(OddJobActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                OddJobActivity.this.editTextOfDatePicker.setText( v_year_month_day_str =year + "-" + (month +1)+"-" +dayOfMonth);
                edOfParticulars.requestFocus();
            }
        },calendar.get(Calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 调用二维码扫描
     */
    private class edOfEmpName_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OddJobActivity.this, CaptureActivity.class);
            startActivityForResult(intent,REQUEST_GET_Name_By_QRCode);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_GET_Name_By_QRCode:
                if(resultCode==RESULT_OK){
                    String returnedEmpName= data.getStringExtra("result");
                    this.edOfEmpName.setText(returnedEmpName);
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("emp_name",returnedEmpName);
                    try {
                        //判断员工姓名是否存在
                        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfIfExistsTheEmpNameAction), map, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                LogUtil.d("OddJobActivity", e.toString());
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseData = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if ("0".equals(responseData)) {
                                            //无此用户，请尽快在PC端维护此用户信息。
                                            Toast.makeText(OddJobActivity.this, "无此用户，请尽快在PC端维护此用户信息！", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        OddJobActivity.this.btnSubmitOddJob.requestFocus();
                                    }
                                });
                            }
                        });
                    }catch(Exception ex){
                        Toast.makeText(OddJobActivity.this,ex.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            break;
            default:break;
        }
    }

    private class btn_back_to_MainActivity_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OddJobActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class appCompatSpinnerOfLineName_OnItemSelectedListenerImpl implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if(isSpinnerFirst){
                view.setVisibility(View.INVISIBLE);
                //关闭开关
                isSpinnerFirst = false;
                return;
            }
            view.setVisibility(View.VISIBLE);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}