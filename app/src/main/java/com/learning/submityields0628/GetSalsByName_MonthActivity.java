package com.learning.submityields0628;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.Enum.Enum_GetSalsByName_Month;
import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.submityields0628.ui.main.SectionsPagerAdapter;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
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

public class GetSalsByName_MonthActivity extends BaseActivity {
    String v_emp_name ;
    private List<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList = new ArrayList<V_GetPieceworkSalsByName_Month>();
    private List<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList =new ArrayList<V_GetOddSalsByName_Month>();;
    private double totalSals = 0.0;
    private Map<String,String> map_GetSalsByName_MonthAction = new HashMap<String, String>();
    private double overtimePay;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sals_by_name__month);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSalsByMAC();

    }

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
                            //禁止使用按钮。
                        }
                    });
                    return;
                }
                v_emp_name = result;
//                edOfDP.getText().toString();
                String v_year_month_str = "2020-07";
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

                            //tv_amount_of_money_piecework_sum.setText(pieceworkSum.toString());
                            tv_title.setText("计件: "+pieceworkSum.toString());
                            totalSals+=pieceworkSum;
//                            tv_total_sals.setText(String.valueOf(totalSals));
                        }
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
//                            tv_amount_of_money_odd_sum.setText(oddJobSum.toString());
                            totalSals+=oddJobSum;
//                            tv_total_sals.setText(String.valueOf(totalSals));
                            tv_title.setText(tv_title.getText() + " 零活："+oddJobSum.toString());
                        }
                    }
                });
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
}