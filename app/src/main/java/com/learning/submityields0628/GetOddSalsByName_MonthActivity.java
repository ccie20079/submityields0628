package com.learning.submityields0628;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.learning.gson.V_GetOddSalsByName_Month;

import java.util.List;

public class GetOddSalsByName_MonthActivity extends AppCompatActivity {
    ListView lv_getOddSalsByName_Month;
    Button btnBackToSalsSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_odd_sals_by_name_month);
        lv_getOddSalsByName_Month = (ListView)findViewById(R.id.lv_getOddSalsByName_Month);
        btnBackToSalsSummary = (Button) findViewById(R.id.btnBackToSalsSummary);

        List<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList = (List<V_GetOddSalsByName_Month>) getIntent().getSerializableExtra("V_GetOddSalsByName_Month");
        GetOddSalsByName_MonthAdapter getOddSalsByName_monthAdapter = new GetOddSalsByName_MonthAdapter(GetOddSalsByName_MonthActivity.this,R.layout.getoddsalsbyname_month_item,v_getOddSalsByName_monthList);
        lv_getOddSalsByName_Month.setAdapter(getOddSalsByName_monthAdapter);
        btnBackToSalsSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}