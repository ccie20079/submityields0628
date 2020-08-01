package com.learning.submityields0628;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.learning.gson.Emp_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.NetworkHelper;
import com.learning.utils.Utility;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * 添加 滑动菜单
 */
public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    NavigationView navView;
    private TextView tv_username;
    private TextView tv_mac;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)    //>=5.0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //使Toolbar的功能和ActionBar一致
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(GravityCompat.START);  //默认开启滑动菜单
        navView = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        tv_username = (TextView)headerView.findViewById(R.id.tv_username);  //tv_username:  显示用户名
        tv_mac = (TextView)headerView.findViewById(R.id.tv_mac);
        navView.setCheckedItem(R.id.add_Emp_info);  //默认选中员工注册。
        navView.setNavigationItemSelectedListener(new navView_OnNavigationItemSelectedListenerImpl());
//        //使用授权
//        PermissionX.init(this).permissions(Manifest.permission.READ_PHONE_STATE).request(new RequestCallback() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
//                if (allGranted) {
//
//                } else {
//                    //此RequestCallback中可直接操作UI
//                    Toast.makeText(MyApplication.getContext(),"您拒绝READ_PHONE_STATE权限的获取,将无法获取您的MAC.",Toast.LENGTH_SHORT).show();
//                    disableAllMenu();
//                    return;
//                }
//            }
//         });
        try {
            getMAC();
        } catch (SocketException e) {
            LogUtil.d("MainActivity",e.toString());
        }
    }
    private class navView_OnNavigationItemSelectedListenerImpl implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //mDrawerLayout.closeDrawers();
            //进入工资查询页面。
            switch(menuItem.getItemId()){
                case R.id.get_sals_to_excel:
                    Intent intent  = new Intent(MainActivity.this, GetSalsByName_MonthActivity_backup.class);
                    startActivity(intent);
                    break;
                case R.id.submit_piecework_quantities:
                    intent  = new Intent(MainActivity.this,ChooseProductNameMainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.submit_odd_amount_of_money:
                    intent  = new Intent(MainActivity.this,OddJobActivity.class);
                    startActivity(intent);
                    break;
                case R.id.show_line_station_process:
                    intent = new Intent(MainActivity.this, V_Style_Line_Activity.class);
                    startActivity(intent);
                    break;
                case R.id.add_Emp_info:
                    intent = new Intent(MainActivity.this,AddEmpActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    private void getMAC() throws SocketException {
        MyApplication.MAC= NetworkHelper.getMacAddress();
        tv_mac.setText(""+MyApplication.MAC);
        Map<String,String> map =new HashMap<>();
        //判断此mac是否已经注册：
        map.put("mac",MyApplication.MAC);
        judgeIfExistsMAC(map);
        return;
    }
    private void judgeIfExistsMAC(Map<String, String> map) {
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetEmpInfoByMAC), map, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(getTheTAGOfTheCurrActivity(),e.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Emp_Info emp_info = Utility.getEmpInfo(responseData);
                MyApplication.setEmp_info(emp_info);
                ConfigureMenu(MyApplication.getEmp_info());
             }
        });
    }

    /**
     * 依据 返回的Emp_Info设置菜单权限
     * @param empInfo
     */
    private void ConfigureMenu(final Emp_Info empInfo){
        //已经注册。获取MenuItem
        Menu menu = navView.getMenu();
        for(int i =0;i<menu.size();i++){
            final MenuItem menuItem = menu.getItem(i);
            final int itemId = menuItem.getItemId();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (itemId){
                        case R.id.submit_piecework_quantities:
                        case R.id.submit_odd_amount_of_money:
                            menuItem.setEnabled(null!=empInfo&&"monitor".equals(empInfo.getRole())?true:false);
                            break;
                        case R.id.get_sals_to_excel:
                        case R.id.show_line_station_process:
                            menuItem.setEnabled(null != empInfo);
                            break;
                        default:
                            tv_username.setText(null==empInfo?"未注册":empInfo.getEmp_name());
                            break;
                    }
                }
            });
        }
    }
    private void disableAllMenu(){
        //已经注册。获取MenuItem
        Menu menu = navView.getMenu();
        menu.setGroupEnabled(0,false);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
           }
        return true;
    }
}