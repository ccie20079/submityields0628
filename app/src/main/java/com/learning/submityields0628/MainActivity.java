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
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.learning.utils.BaseActivity;

import org.apache.log4j.chainsaw.Main;


/**
 * 添加 滑动菜单
 */
public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)    //>=5.0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //默认开启滑动菜单
        mDrawerLayout.openDrawer(GravityCompat.START);
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
        }
        navView.setCheckedItem(R.id.get_sals_to_excel);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //mDrawerLayout.closeDrawers();
                //进入工资查询页面。
                switch(menuItem.getItemId()){
                    case R.id.get_sals_to_excel:
                        Intent intent  = new Intent(MainActivity.this,GetSalsByName_MonthActivity.class);
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
        });
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
        //return super.onOptionsItemSelected(item);
        return true;
    }
}