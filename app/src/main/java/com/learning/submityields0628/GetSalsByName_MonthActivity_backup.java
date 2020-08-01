package com.learning.submityields0628;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.learning.Enum.Enum_GetSalsByName_Month;
import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.gson.V_GetSamePYButWritting;
import com.learning.gson.V_Overtime_Detail;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.HttpUtil;
import com.learning.utils.JxlExcelHelper;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.UriHelper;
import com.learning.utils.Utility;
import com.learning.utils.WPSHelper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetSalsByName_MonthActivity_backup extends BaseActivity {
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

    //emp_info中的 emp_name,依据MAC查询得到
    String v_emp_name ;
    private List<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList = new ArrayList<V_GetPieceworkSalsByName_Month>();
    private List<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList =new ArrayList<V_GetOddSalsByName_Month>();;
    private double totalSals = 0.0;
    private Map<String,String> map_GetSalsByName_MonthAction = new HashMap<String, String>();
    private double overtimePay;     //加班工时。
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
         getSalsByMAC();

    }

    private void getPieceworkTotalSalsByN_M(Map<String, String> map_getSalsByName_monthAction) {
         HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetPieceworkTotalSalsByN_M), map_getSalsByName_monthAction, new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
             }
             @Override
             public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                if(responseData == ""||responseData ==null) return;
                //此请求对应的函数返回的是一个number类型数据。
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GetSalsByName_MonthActivity_backup.this.tv_amount_of_money_piecework_sum.setText(responseData);
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
    private void getSalsByMAC() {

        final String Mac = MyApplication.MAC;
        Map<String,String> map =new HashMap<String, String>();
        map.put("v_mac",Mac);
        //查询对应的员工姓名
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpNameByMACAction), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(), e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result == null || result == "") {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.getContext(), "此MAC尚未注册，请先导入至emp_info中！", Toast.LENGTH_LONG).show();
                            btn_downloadSalsExcelByName_MonthAction.setEnabled(false);
                        }
                    });
                    return;
                }
                v_emp_name = result;
                String v_year_month_str = edOfDP.getText().toString();
                map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_emp_name.toString(), v_emp_name);
                map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_mac.toString(), MyApplication.MAC);
                map_GetSalsByName_MonthAction.put(Enum_GetSalsByName_Month.v_year_and_month_str.toString(), v_year_month_str);
                //获取相应的计件工资总信息。
                getPieceworkSalsByName_MonthAction(map_GetSalsByName_MonthAction);
                //获取相应的零活工资总信息
                getOddSalsByName_MonthAction(map_GetSalsByName_MonthAction);
                //先判断该用户是否和AR_Summary_Final中的用户名一致
                IfExistsSameNameInAR_S_F(v_emp_name, v_year_month_str);
                //获取加班费用的总信息
                //getOvertimePayByName_Month(map_GetSalsByName_MonthAction);
            }
        });
    }
    private void IfExistsSameNameInAR_S_F(String v_emp_name, String v_year_month_str) {
        final Map<String,String> map = new HashMap<String,String>();
        map.put("v_emp_name",v_emp_name);
        map.put("v_year_and_month_str",v_year_month_str);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfIfExistsSameNameInAR_S_F), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                if(Integer.parseInt(responseData) ==1){
                    //有姓名完全相同的考勤人员记录。
                    //直接调用，考勤工时获取方法。
                    getOvertimePayByName_Month(map_GetSalsByName_MonthAction);
                    return;
                }
                //获取拼音相同的考勤人员记录。让用户选择。
                urlOfGetSamePYButWritting(map);
            }
        });
    }

    /**
     * //获取拼音相同的考勤人员记录。让用户选择。
     * @param map
     */
    private void urlOfGetSamePYButWritting(final Map<String, String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetSamePYButWritting), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                final List<V_GetSamePYButWritting> v_getSamePYButWrittingList = Utility.getSamePYButWrittingList(responseData);
                if(v_getSamePYButWrittingList.size()==0){
                    //为找到相似用户。
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.getContext(),"AR_Summary_Final中，没有同名用户，也没有拼音相同的用户，请联系班长或考勤管理人员！",Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final CharSequence[] charSequences = v_getSamePYButWrittingList.toArray(new CharSequence[v_getSamePYButWrittingList.size()]);
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(GetSalsByName_MonthActivity_backup.this);
                        dialog.setTitle(String.format("AR_Summary_Final中 存在拼音相同，但书写不同的记录，请选择！"));
                        dialog.setCancelable(false);
                        dialog.setSingleChoiceItems(charSequences, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                //更新emp_info中相应的ar_name,ar_job_number,ar_year_and_month
//                        v_getSamePYButWrittin_choosed[0] = (V_GetSamePYButWritting) charSequences[which];
                                map.put("v_asf_job_number",((V_GetSamePYButWritting) charSequences[position]).getJob_number());
                            }
                        });

//                        dialog.setItems(charSequences, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });

                        dialog.setPositiveButton("依据选择获取考勤记录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(map.get("v_asf_job_number")==null) {
                                    Toast.makeText(MyApplication.getContext(), "请选择需要关联的用户。", Toast.LENGTH_LONG).show();
                                    return;
                                }
                              urlOfUpdateEmpInfoByASF_JobNumber(map);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        dialog.show();
                    }
                });
            }
        });
    }

    /**
     * 依据工号更新 emp_info中的数据。
     * @param map
     */
    private void urlOfUpdateEmpInfoByASF_JobNumber(final Map<String, String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfUpdateEmpInfoByASF_JobNumber), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String resposeData = response.body().string();
                if(Integer.parseInt(resposeData)==1){
                    //更新成功，进行获取。
                    getOvertimePayByName_Month(map);
                    return ;
                }
                //没有更新成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(), "关联拼音相同的考勤记录失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                });
                return;
            }
        });
    }

    private void getOvertimePayByName_Month(Map<String, String> map_getSalsByName_monthAction) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetOvertimePayByName_Month), map_getSalsByName_monthAction, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if("".equals(result)){
                    overtimePay = 0;
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_overtime_pay_sum.setText(result);
                        overtimePay = Double.parseDouble(result);
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
                 LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
             }
             @Override
             public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if("[]".equals(result)) return;
                v_getOddSalsByName_monthList = (ArrayList<V_GetOddSalsByName_Month>) Utility.getListOfT(result,V_GetOddSalsByName_Month[].class);

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
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if("[]".equals(responseData)) return;
                  v_getPieceworkSalsByName_monthList = (ArrayList<V_GetPieceworkSalsByName_Month>) Utility.getListOfT(responseData,V_GetPieceworkSalsByName_Month[].class);

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

            Intent intent = new Intent(GetSalsByName_MonthActivity_backup.this,GetPieceWorkSalsByName_MonthActivity.class);
            intent.putExtra("V_GetPieceworkSalsByName_Month", (Serializable) v_getPieceworkSalsByName_monthList);
            startActivity(intent);
        }
    }

    private class tv_amount_of_money_odd_detail_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GetSalsByName_MonthActivity_backup.this,GetOddSalsByName_MonthActivity.class);
            intent.putExtra("V_GetOddSalsByName_Month", (Serializable) v_getOddSalsByName_monthList);
            startActivity(intent);
        }
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(GetSalsByName_MonthActivity_backup.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GetSalsByName_MonthActivity_backup.this.edOfDP.setText(year + "-" + (month +1));
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
            File xlsFile = new File(getExternalCacheDir(),fileName);//Android\data\com.learning.submityields0628
           // File xlsFile = new File("/sdcard/Android/data",fileName);
//            PermissionX.init(GetSalsByName_MonthActivity.this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
//                @Override
//                public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
//                    if(allGranted){
//                        Toast.makeText(GetSalsByName_MonthActivity.this,"WRITE_EXTERNAL_STORAGE previledge was got",Toast.LENGTH_LONG).show();
//                        return;
//                    }else{
//                        Toast.makeText(GetSalsByName_MonthActivity.this,"you denied the previledges!",Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                }
//            });
            try {
                if(xlsFile.exists()){
                    xlsFile.delete();
                    xlsFile.createNewFile();
                }else {
                    xlsFile.createNewFile();//创建出来
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            V_Overtime_Detail v_overtime_detail = new V_Overtime_Detail("加班工资",overtimePay);
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
                    new ArrayList[]{(ArrayList) v_getPieceworkSalsByName_monthList,
                            (ArrayList) v_getOddSalsByName_monthList,
                            (ArrayList) v_overtime_detailsList});
            jxlExcelHelper.close();
            Toast.makeText(MyApplication.getContext(), "导出Excel成功: " + xlsFile.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
            //调用WPS打开
            //WPSHelper.openFile(xlsFile.getAbsolutePath().toString());
            Uri contentUri = UriHelper.getUriOfFile(xlsFile,"com.learning.submityields0628.fileprovider");
            Intent intent = WPSHelper.getWordFileIntent(xlsFile,contentUri);
            startActivity(intent);
        }
    }

    private class tv_back_to_MainActivity_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GetSalsByName_MonthActivity_backup.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}