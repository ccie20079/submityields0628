package com.learning.submityields0628;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;
import com.learning.gson.MSG;
import com.learning.gson.V_Daily_Record;
import com.learning.gson.V_Emp_Name;
import com.learning.gson.V_Specific_Process;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.Utility;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SubmitYieldsActivity extends BaseActivity {
    private static final String TAG = "SubmitYieldsActivity";
    private static final int REQUEST_TO_GET_STATION_NAME = 2;
    private static final int REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE = 3;
    private static final int REQUEST_TO_GET_EMP_NAME_WITH_PN = 4;
    private static final int REQUEST_TO_CHOOSE_PN = 5;
    private static final int REQUEST_GET_ALL_EMPS = 6;
    private static final int REQUEST_TO_GET_THE_SPECIFIC_PROCESS = 7;
    private static final int REQUEST_TO_GET_EMP_NAME_BY_QRCode = 8;

    private EditText editTextOfDatePicker = null;
    private EditText editTextOfStationName = null;
    private EditText editTextOfEmpName = null;
    //工序名
    private EditText editTextOfSpecificProcess = null;
    private EditText editTextOfQuantitiesOfYieldsToSubmit = null;

    private Button btnSubmit = null;
    private Button btnToChoosePN = null;
    //工单标题
    private TextView tv_productOrder = null;
    private TextView tv_styleName;
    List<V_Emp_Name> v_emp_name_of_pn_line_list = null;
    List<V_Emp_Name> v_emp_name_of_pn_list = null;

    private String currLineName = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_yields);
        //产品名称显示
        tv_productOrder = (TextView)findViewById(R.id.tv_productOrder);
        tv_styleName = (TextView)findViewById(R.id.tv_styleName);
        this.editTextOfDatePicker = (EditText)findViewById(R.id.editTextOfDatePicker);
        this.editTextOfStationName = (EditText)super.findViewById(R.id.editTextOfStationName);
        this.editTextOfEmpName = (EditText)super.findViewById(R.id.editTextOfEmpName);
        this.editTextOfSpecificProcess = (EditText)super.findViewById(R.id.editTextOfSpecificProcess);
        this.btnSubmit = (Button)super.findViewById(R.id.btnSubmitYields);
        this.btnToChoosePN = (Button) super.findViewById(R.id.btnToChoosePNInfo);
        this.editTextOfQuantitiesOfYieldsToSubmit = (EditText)super.findViewById(R.id.editTextOfQuantities);
        //注册监听器
        editTextOfStationName.setOnClickListener(new EditTextOfStationName_onClickListenerImpl());
        editTextOfEmpName.setOnClickListener(new EditTextOfEmpName_OnClickListenerImpl());
        this.editTextOfDatePicker.setOnTouchListener(new editTextOfDatePicker_onTouchListenerImpl());
        this.editTextOfDatePicker.setOnFocusChangeListener(new editTextOfDatePicker_onFocusChangeListenerImpl());
        this.btnToChoosePN.setOnClickListener(new BtnToChoosePN_OnClickViewListenerImpl());
        this.editTextOfSpecificProcess.setOnClickListener(new EditTextOfSpecificProcess_OnClickViewListenerImpl());
        this.btnSubmit.setOnClickListener(new BtnSubmitYields_OnClickListenerImpl());


        //设置工单
        this.tv_productOrder.setText(getIntent().getStringExtra("product_order"));
        //设置款式
        this.tv_styleName.setText(getIntent().getStringExtra("style_name"));
        //设置日期
        this.editTextOfDatePicker.setText(DateHelper.getTodayStr());
        /**
         * ***********暂时不用获取该产品下的所有员工，当线体下没有员工日产量记录时，以作备用。
         */
        //getAllEmpInfosWithPN(****************.getText().toString());
        lostFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    /**
     * 处理返回的信息。
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //获取线体的扫描结果,依据线体，款式获取对应的工序。
            case REQUEST_TO_GET_STATION_NAME:
                if(resultCode==RESULT_OK){
                    String returnedStationName= data.getStringExtra("result");
                    this.editTextOfStationName.setText(returnedStationName);
                    //焦点移动
                    //判断工位标识中是否存在"_"
                    if(returnedStationName.indexOf("_")<=0){
                        Toast.makeText(SubmitYieldsActivity.this,"工位格式不对，应为：测试组_1号",Toast.LENGTH_LONG).show();
                        SubmitYieldsActivity.this.editTextOfStationName.setText("");
                        SubmitYieldsActivity.this.editTextOfSpecificProcess.setText("");
                        return;
                    }

                    //这里对线体拆分
                    currLineName = returnedStationName.split("_")[0];
                    //依据工单，工位编号获取对应的工序
                    //异步获取
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("style_name",tv_styleName.getText().toString());
                    map.put("line_name",currLineName);
                    map.put("station_name",returnedStationName.split("_")[1]);
                    judgeLineAndStationName(map);
                }
            break;
                //扫描二维码获取员工姓名
            case REQUEST_TO_GET_EMP_NAME_BY_QRCode:
                String returnedEmpName= data.getStringExtra("result");
                this.editTextOfEmpName.setText(returnedEmpName);
                Map<String,String> map = new HashMap<String,String>();
                map.put("emp_name",returnedEmpName);
                //判断员工姓名是否存在
                HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfIfExistsTheEmpNameAction), map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.d("SubmitYieldsActivity",e.toString());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       final String responseData= response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if("0".equals(responseData)){
                                    //无此用户，请尽快在PC端维护此用户信息。
                                    Toast.makeText(SubmitYieldsActivity.this,"无此用户，请尽快在PC端维护此用户信息！",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SubmitYieldsActivity.this.editTextOfQuantitiesOfYieldsToSubmit.requestFocus();
                            }
                        });
                    }
                });
                break;
                /**
                 * 获取线体下的员工
                 */
            case REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE:
                if(resultCode==RESULT_OK){
                    returnedEmpName = data.getStringExtra("emp_name");
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            case REQUEST_TO_GET_EMP_NAME_WITH_PN:
                if(resultCode==RESULT_OK){
                    returnedEmpName = data.getStringExtra("emp_name");
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            case  REQUEST_TO_CHOOSE_PN:
                if(resultCode==RESULT_OK){
                    String returnedPNOrderName = data.getStringExtra("product_order");
                    this.tv_productOrder.setText(returnedPNOrderName);
                }
                break;
            case REQUEST_GET_ALL_EMPS:
                if(resultCode==RESULT_OK){
                    returnedEmpName = data.getStringExtra("emp_name");
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            case REQUEST_TO_GET_THE_SPECIFIC_PROCESS:
                if(RESULT_OK==resultCode){
                    String returnedProcess= data.getStringExtra("the_specific_processes");
                    this.editTextOfSpecificProcess.setText(returnedProcess);
                this.editTextOfSpecificProcess.setSelection(returnedProcess.length());
                }
                break;
            default:
                break;
        }
    }

    private void getProcessByPN_Line_Station(Map<String, String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetProcessByPN_Line_StationAction), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                final List<V_Specific_Process> v_specific_processList = Utility.getProcessByPN_Line_StationAction(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(0==v_specific_processList.size()){
                            Toast.makeText(SubmitYieldsActivity.this,"款式："+tv_styleName.getText()+" 在"+editTextOfStationName.getText()+" 上没有布置工序！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        StringBuffer sb = new StringBuffer();
                        for(V_Specific_Process v_specific_process:v_specific_processList){
                            sb.append(v_specific_process.toString()+"\r\n");
                        }
                        String temp = sb.toString();
                        temp = temp.substring(0,temp.lastIndexOf("\r\n"));
                        SubmitYieldsActivity.this.editTextOfSpecificProcess.setText(temp);
                        //贯标定位至
                        SubmitYieldsActivity.this.editTextOfEmpName.requestFocus();
                    }
                });
            }
        });
    }

    private class editTextOfDatePicker_onTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                showDatePickerDialog();
                //焦点跳转至线体选择框
                SubmitYieldsActivity.this.editTextOfStationName.requestFocus();
                return true;
            }
            return false;
        }
    }
    /**
     * 日期选择监听器
     */
    private class editTextOfDatePicker_onFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                showDatePickerDialog();
            }
        }
    }

    /**
     * 显示日期选择框
     */
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(SubmitYieldsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SubmitYieldsActivity.this.editTextOfDatePicker.setText(year + "-" + (month +1)+"-" +dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
     private void lostFocus(){
        this.editTextOfStationName.requestFocus();
        //this.editTextOfLineName.setShowSoftInputOnFocus(false);
        this.editTextOfEmpName.setShowSoftInputOnFocus(false);
        this.editTextOfStationName.setShowSoftInputOnFocus(false);
        this.editTextOfDatePicker.setShowSoftInputOnFocus(false);
        this.editTextOfSpecificProcess.setShowSoftInputOnFocus(false);
    }
    /**
     * 工作站二维码扫描事件
     */
    private class EditTextOfStationName_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            PermissionX.init(SubmitYieldsActivity.this).permissions(Manifest.permission.CAMERA).request(new RequestCallback() {
                @Override
                public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                    if(allGranted){
                        Intent intent = new Intent(SubmitYieldsActivity.this, CaptureActivity.class);
                        startActivityForResult(intent,REQUEST_TO_GET_STATION_NAME);
                    }
                    else{
                        Toast.makeText(MyApplication.getContext(),"权限不足！",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    /**
     * 转向员工姓名选择页面
     */
    private class EditTextOfEmpName_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //先判断 依据线体是否已经获得了员工信息
            /*
            if(v_emp_name_of_pn_line_list!=null && v_emp_name_of_pn_line_list.size()!=0){
                Intent intent = new Intent(MyApplication.getContext(),ShowEmpNameOfPNAndLineActivity.class);
                intent.putExtra("emp_name_of_pn_line_serializable",(Serializable)v_emp_name_of_pn_line_list);
                startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE);
                return;
            }
            if(v_emp_name_of_pn_list!=null && v_emp_name_of_pn_list.size()!=0){
                Intent intent = new Intent(MyApplication.getContext(),ShowEmpNameOfPNActivity.class);
                intent.putExtra("emp_name_of_pn_serializable",(Serializable)v_emp_name_of_pn_list);
                startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME_WITH_PN);
                return;
            }
            */
            //进入条码扫描页面
            //进入全部员工信息获取页面
            Intent intent = new Intent(SubmitYieldsActivity.this, CaptureActivity.class);
            startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME_BY_QRCode);
        }
    }
    /**
     * 获取该工单，该线体下 最近一天日报表中的员工名单
     * @param products_name
     * @param line_name
     */
    private void getAllEmpInfosWithPN_LineName(final String products_name, final String line_name) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("products_name",products_name);
        map.put("line_name",line_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetAllEmpInfosWithPN_LineName), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                v_emp_name_of_pn_line_list = Utility.getAllEmpInfosRecentByCreatedTimeDesc(responseText);
            }
        });
    }
    /**
     * 获取该工单下  所有最近一天的日报表员工 记录提交记录。
     * @param products_name
     */
    private void getAllEmpInfosWithPN(final String products_name) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("products_name",products_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetAllEmpInfosWithPN), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity: ",e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                v_emp_name_of_pn_list = Utility.getAllEmpInfosRecentByCreatedTimeDesc(responseText);
            }
        });
    }
    /**
     * 回到选择工单Activity
     */
    private class BtnToChoosePN_OnClickViewListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SubmitYieldsActivity.this,ChooseProductNameMainActivity.class);
            startActivityForResult(intent,REQUEST_TO_CHOOSE_PN);
        }
    }
    /**
     * 选择工序监听器,此处不选择。
     */
    private class EditTextOfSpecificProcess_OnClickViewListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /*
            Intent intent=new Intent(SubmitYieldsActivity.this,ChooseSpecificProcessActivity.class);
            intent.putExtra("products_name",textViewOfProductsName.getText().toString());
            startActivityForResult(intent,REQUEST_TO_GET_THE_SPECIFIC_PROCESS);
                         */
        }
    }
    /**
     * 提交产量按钮。
     */
    private class BtnSubmitYields_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Map<String,String> yields_to_submit_map = new HashMap<String,String>();
            if("".equals(tv_productOrder.getText().toString())){
                Toast.makeText(SubmitYieldsActivity.this,"请先选择工单！",Toast.LENGTH_SHORT).show();
                return;
            }
            String stationName;
            if("".equals(stationName=editTextOfStationName.getText().toString())){
                editTextOfStationName.requestFocus();
                return;
            }
            String emp_name;
            if("".equals(emp_name=editTextOfEmpName.getText().toString())){
                editTextOfEmpName.requestFocus();
                return;
            }
            String quantities_str;
            if("".equals(quantities_str =editTextOfQuantitiesOfYieldsToSubmit.getText().toString() )){
                editTextOfQuantitiesOfYieldsToSubmit.requestFocus();
                return;
            }
            //参数全部与数据库保持一致。从数据开始进行。
            yields_to_submit_map.put("v_product_order",tv_productOrder.getText().toString());
            yields_to_submit_map.put("v_style_name",tv_styleName.getText().toString());
            yields_to_submit_map.put("v_line_name",currLineName);
            yields_to_submit_map.put("v_station_name",editTextOfStationName.getText().toString().split("_")[1]);
            yields_to_submit_map.put("v_emp_name",emp_name);
            yields_to_submit_map.put("v_quantities",editTextOfQuantitiesOfYieldsToSubmit.getText().toString());
            yields_to_submit_map.put("v_report_year_month_day_str",editTextOfDatePicker.getText().toString());
            yields_to_submit_map.put("v_time_str_of_the_client",new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
            //先判断是否有相似的记录。提交给Action的键值对可以多，多余的将不被处理，匹配上的将传给Utils
            HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetSameDRecordAction), yields_to_submit_map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.d(TAG,e.toString());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData= response.body().string();
                    Utility<V_Daily_Record> utility = new Utility<V_Daily_Record>();
                    final ArrayList<V_Daily_Record> v_daily_recordList = utility.get_V_Daily_Record_List(responseData);
                    //没有相似数据，进行提交
                    if(null == v_daily_recordList){
                        submitTheDailyRecord(yields_to_submit_map);
                        return;
                    }
                    final CharSequence[] charSequences = v_daily_recordList.toArray(new CharSequence[v_daily_recordList.size()]);
                      //有相似数据，显示。
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(SubmitYieldsActivity.this);
                            dialog.setTitle(SubmitYieldsActivity.this.editTextOfDatePicker.getText().toString()+": 有" + v_daily_recordList.size()+"条相似记录！" );
                            dialog.setCancelable(false);
                            dialog.setItems(v_daily_recordList.toArray(charSequences), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.setPositiveButton("继续提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                submitTheDailyRecord(yields_to_submit_map);
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
            });
            //提交完成后，使此按钮
            //提交
        }
    }
    private void submitTheDailyRecord(Map<String,String> map){
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfSaveDailyRecordAction), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData= response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(SubmitYieldsActivity.this.tv_productOrder,"已提交: "+Integer.parseInt(responseData)+"行。",Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    //先判断线体，工位是否存在
    void judgeLineAndStationName(final Map<String,String> map){

        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfJudgeLineAndStationName), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                final MSG msg  = Utility.getMSG(responseData);
                if(!msg.isFlag()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SubmitYieldsActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }
                //获取线体，工站，款式对应的工序信息
                getProcessByPN_Line_Station(map);
            }
        });
    }
}
