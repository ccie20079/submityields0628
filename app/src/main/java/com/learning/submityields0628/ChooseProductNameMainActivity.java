package com.learning.submityields0628;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.learning.Adapter.ProductInfosRecentAdapter;
import com.learning.gson.V_Products_Order;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseProductNameMainActivity extends BaseActivity {
    private RecyclerView recyclerViewOfProducts = null;
    //private List<V_Products_Info_Recent> v_products_info_recentList= null;
    List<V_Products_Order> v_products_orderList = null;
    TextView tv_back_to_MainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product_name_main);
        tv_back_to_MainActivity = (TextView)findViewById(R.id.tv_back_to_MainActivity);
        recyclerViewOfProducts = (RecyclerView)super.findViewById(R.id.recyclerViewOfProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfProducts.setLayoutManager(layoutManager);
        //加载数据。
        getDataToList();
        tv_back_to_MainActivity.setOnClickListener(new tv_back_to_MainActivity_onClickListenerImpl());
    }

    private void getDataToList() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllProductInfosRecent), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("ChooseProductNameMainActivity",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                v_products_orderList = Utility.getAllProductsOrderInfo(responseData);

                LogUtil.d("ChooseProductNameMainActivity",v_products_orderList.toString());
                //更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductInfosRecentAdapter adapter = new ProductInfosRecentAdapter(v_products_orderList);
                        recyclerViewOfProducts.setAdapter(adapter);
                    }
                });
            }
        });

    }

    private class tv_back_to_MainActivity_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ChooseProductNameMainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}