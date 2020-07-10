package com.learning.submityields0628;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.learning.Enum.Enum_GetSalsByName_Month;
import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.gson.V_Overtime_Detail;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.HttpUtil;
import com.learning.utils.JxlExcelHelper;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.Utility;
import com.learning.utils.WPSHelper;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableSheet;
import jxl.write.biff.JxlWriteException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetSalsByName_MonthActivity extends BaseActivity {
    EditText edOfDP = null;
    TextView tv_amount_of_money_piecework_sum;   //计件总收入。
    TextView tv_amount_of_money_odd_sum;
    TextView tv_overtime_pay_sum;
    TextView tv_total_sals;

    TextView tv_amount_of_money_piecework_detail;
    TextView tv_amount_of_money_odd_detail;
    TextView btn_downloadSalsExcelByName_MonthAction;
    TextView tv_of_result;
    TextView tv_back_to_MainActivity;


    String v_emp_name ;
    private ArrayList<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList;
    private ArrayList<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList;
    private double totalSals = 0.0;
    private Map<String,String> map_GetSalsByName_MonthAction = new HashMap<String, String>();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sals_by_name__month_action);
        edOfDP = findViewById(R.id.edOfDP);

        tv_amount_of_money_piecework_sum = (TextView)findViewById(R.id.tv_amount_of_money_piecework_sum);
        tv_amount_of_money_odd_sum= (TextView)findViewById(R.id.tv_amount_of_money_odd_sum);
        tv_overtime_pay_sum= (TextView)findViewById(R.id.tv_overtime_pay_sum);
        tv_total_sals= (TextView)findViewById(R.id.tv_total_sals);
        tv_amount_of_money_odd_detail = (TextView)findViewById(R.id.tv_amount_of_money_odd_detail);
        tv_amount_of_money_piecework_detail= (TextView)findViewById(R.id.tv_amount_of_money_piecework_detail);
         btn_downloadSalsExcelByName_MonthAction = (Button)findViewById(R.id.btn_downloadSalsExcelByName_MonthAction);
         tv_of_result= (TextView)findViewById(R.id.tv_of_result);
         tv_back_to_MainActivity = (TextView)findViewById(R.id.tv_back_to_MainActivity);


        edOfDP.setText(DateHelper.getCurrMonthStr());
        edOfDP.setOnTouchListener(new edOfDP_onTouchListenerImpl());
        tv_amount_of_money_piecework_detail.setOnClickListener(new tv_amount_of_money_piecework_detail_onClickListenerImpl());
        tv_amount_of_money_odd_detail.setOnClickListener(new tv_amount_of_money_odd_detail_onClickListenerImpl());
         btn_downloadSalsExcelByName_MonthAction.setOnClickListener(new btn_downloadSalsExcelByName_MonthAction_onClickListenerImpl());
         tv_back_to_MainActivity.setOnClickListener(new tv_back_to_MainActivity_onClickListenerImpl());


         //获取本手机的ＩＭＥＩ
        getIMEIOfThePhone();

    }

    private void getPieceworkTotalSalsByN_M(Map<String, String> map_getSalsByName_monthAction) {
         HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetPieceworkTotalSalsByN_M), map_getSalsByName_monthAction, new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
             }
             @Override
             public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                if(responseData == ""||responseData ==null) return;
                //此请求对应的函数返回的是一个number类型数据。
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GetSalsByName_MonthActivity.this.tv_amount_of_money_piecework_sum.setText(responseData);
                        totalSals+=Double.parseDouble(responseData);
                        tv_total_sals.setText(String.valueOf(totalSals));
                    }
                });
             }
         });
    }

    /**
     * 获取本机手机号码android.permission.READ_SMS"/>
     *     <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
     *     <uses-permission android:name="android.permission.READ_PHONE_STATE
     */
    private void getIMEIOfThePhone() {
        final TelephonyManager tm = (TelephonyManager) this.getSystemService(getApplication().TELEPHONY_SERVICE);
        PermissionX.init(this).permissions(Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_PHONE_STATE).request(new RequestCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if(allGranted){
                    final String Imei = tm.getImei(0);
                    Map<String,String> map =new HashMap<String, String>();
                    map.put("v_imei",Imei);
                    //查询对应的员工姓名
                    HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpNameByIMEIAction), map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            if(result ==null||result==""){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyApplication.getContext(),"此IMEI尚未注册，请在导入至emp_info中！",Toast.LENGTH_LONG).show();
                                       }
                                });
                                return;
                            }
                            v_emp_name = result;
                            String v_year_month_str = edOfDP.getText().toString();
                            map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_emp_name.toString(),v_emp_name);
                            map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_imei.toString(),Imei);
                            map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_year_month_str.toString(),v_year_month_str);
                            //获取相应的计件工资总信息。
                            getPieceworkSalsByName_MonthAction(map_GetSalsByName_MonthAction);
                            //获取相应的零活工资总信息
                            getOddSalsByName_MonthAction(map_GetSalsByName_MonthAction);
                            //获取加班费用的总信息
                            getOvertimePayByName_Month(map_GetSalsByName_MonthAction);
                        }
                    });
                }
            }
        });

    }

    private void getOvertimePayByName_Month(Map<String, String> map_getSalsByName_monthAction) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetAllOverTimeByIMEI_MonthStr), map_getSalsByName_monthAction, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if("".equals(result)) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_overtime_pay_sum.setText(result);
                        totalSals+=Double.parseDouble(result);
                        tv_total_sals.setText(String.valueOf(totalSals));
                        //Toast.makeText(MyApplication.getContext(),result,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getOddSalsByName_MonthAction(Map<String,String> map) {
         HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetOddSalsByName_Month), map, new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
             }
             @Override
             public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if("".equals(result)) return;
                v_getOddSalsByName_monthList = Utility.getOddSalsByName_monthList(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Double oddJobSum;
                        if(null==v_getOddSalsByName_monthList||v_getOddSalsByName_monthList.size()==0){
                            oddJobSum = 0.0;
                        }else{
                            oddJobSum=v_getOddSalsByName_monthList.get(v_getOddSalsByName_monthList.size()-1).getAmount_of_money_sum();
                            tv_amount_of_money_odd_sum.setText(oddJobSum.toString());
                            totalSals+=oddJobSum;
                            tv_total_sals.setText(String.valueOf(totalSals));
                        }
                    }
                });
             }
         });
    }

    private void getPieceworkSalsByName_MonthAction(Map<String,String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetPieceworkSalsByName_Month), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if(responseData=="") return;
                v_getPieceworkSalsByName_monthList = Utility.getPieceworkSalsByName_monthList(responseData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Double pieceworkSum;
                        if(null==v_getPieceworkSalsByName_monthList||0==v_getPieceworkSalsByName_monthList.size()){
                            pieceworkSum = 0.0;
                        }else{
                            pieceworkSum =  v_getPieceworkSalsByName_monthList.get(v_getPieceworkSalsByName_monthList.size()-1).getAmount_of_money_sum();

                            tv_amount_of_money_piecework_sum.setText(pieceworkSum.toString());
                            totalSals+=pieceworkSum;
                            tv_total_sals.setText(String.valueOf(totalSals));
                        }
                    }
                });
            }
        });
    }

    private class tv_amount_of_money_piecework_detail_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(GetSalsByName_MonthActivity.this,GetPieceWorkSalsByName_MonthActivity.class);
            intent.putExtra("V_GetPieceworkSalsByName_Month", (Serializable) v_getPieceworkSalsByName_monthList);
            startActivity(intent);
        }
    }

    private class tv_amount_of_money_odd_detail_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GetSalsByName_MonthActivity.this,GetOddSalsByName_MonthActivity.class);
            intent.putExtra("V_GetOddSalsByName_Month", (Serializable) v_getOddSalsByName_monthList);
            startActivity(intent);
        }
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(GetSalsByName_MonthActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GetSalsByName_MonthActivity.this.edOfDP.setText(year + "-" + (month +1));
            }
        },calendar.get(Calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private class edOfDP_onTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                showDatePickerDialog();
                tv_amount_of_money_piecework_detail.requestFocus();
                return true;
            }
            return false;
        }
    }

    /**
     * 下载此人的工资excel
     */

    private class btn_downloadSalsExcelByName_MonthAction_onClickListenerImpl implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

            String fileName = v_emp_name+"_"+edOfDP.getText().toString()+".xls";
            File xlsFile = new File(getExternalCacheDir(),fileName);
            try {
                if(xlsFile.exists()){
                    xlsFile.delete();
                }else {
                    xlsFile.createNewFile();//创建出来
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            V_Overtime_Detail v_overtime_detail = new V_Overtime_Detail("加班工资",Double.parseDouble(tv_overtime_pay_sum.getText().toString()));
            List<V_Overtime_Detail> v_overtime_detailsList = new ArrayList<>();
            v_overtime_detailsList.add(v_overtime_detail);
            JxlExcelHelper jxlExcelHelper = new JxlExcelHelper(xlsFile);
            jxlExcelHelper.create();
            /*
            jxlExcelHelper.writeObjListToExcel(jxlExcelHelper.getFirstSheet("计件工资详情"),V_GetPieceworkSalsByName_Month.class,v_getPieceworkSalsByName_monthList,MyApplication.getContext());
            //Toast.makeText(MyApplication.getContext(),"excel已导出至：" +xlsFile.getPath(),Toast.LENGTH_SHORT).show();
            jxlExcelHelper.writeObjListToExcel(jxlExcelHelper.getSecondSheet("零活工资详情"),V_GetOddSalsByName_Month.class,v_getOddSalsByName_monthList,MyApplication.getContext());

            jxlExcelHelper.writeObjListToExcel(jxlExcelHelper.getThirdSheet("加班详情"),V_Overtime_Detail.class,v_overtime_detailsList,MyApplication.getContext());
            */
            jxlExcelHelper.writeMultiListsToFirstExcel("工资详情",
                    MyApplication.getContext(),
                    new ArrayList[]{v_getPieceworkSalsByName_monthList,
                    v_getOddSalsByName_monthList,
                            (ArrayList) v_overtime_detailsList});
            jxlExcelHelper.close();
            Toast.makeText(MyApplication.getContext(), "导出Excel成功: " + xlsFile.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
            //调用WPS打开
            //WPSHelper.openFile(xlsFile.getAbsolutePath().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            File xlsDir = new File(String.valueOf(getExternalCacheDir()));    //files_root 在xml/file_paths中定义。
            File xlsFilePath = new File(xlsDir,fileName);
            Uri contentUri = FileProvider.getUriForFile(MyApplication.getContext(),"com.learning.submityields0628.fileprovider",xlsFilePath);
            intent.setData(contentUri);
            startActivity(intent);
        }
    }

    private class tv_back_to_MainActivity_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GetSalsByName_MonthActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}