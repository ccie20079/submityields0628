package com.learning.submityields0628;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.utils.BaseActivity;

import java.util.List;

public class GetPieceWorkSalsByName_MonthActivity extends BaseActivity {
    ListView lv_getPieceworkSalsByName_Month;
    Button btnBackToSalsSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_piece_work_sals_by_name__month);
        lv_getPieceworkSalsByName_Month = (ListView)findViewById(R.id.lv_getPieceworkSalsByName_Month);
        btnBackToSalsSummary = (Button) findViewById(R.id.btnBackToSalsSummary);

        List<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList = (List<V_GetPieceworkSalsByName_Month>) getIntent().getSerializableExtra("V_GetPieceworkSalsByName_Month");
        GetPieceworkSalsByName_MonthAdapter getPieceworkSalsByName_monthAdapter = new GetPieceworkSalsByName_MonthAdapter(GetPieceWorkSalsByName_MonthActivity.this,R.layout.getpieceworksalsbyname_month_item,v_getPieceworkSalsByName_monthList);
        lv_getPieceworkSalsByName_Month.setAdapter(getPieceworkSalsByName_monthAdapter);
        btnBackToSalsSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}