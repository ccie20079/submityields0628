package com.learning.submityields0628;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.gson.V_Line_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowAllLinesInfoOrderByCreatedTimeActivity extends BaseActivity {
    SimpleAdapter simpleAdapter = null;
    List<V_Line_Info> line_info_list ;
    private ListView listView = null;
    private String result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_lines_info_order_by_created_time);
         this.listView = (ListView)findViewById(R.id.listViewOfLineInfo);
        getLineInfoOrderByCreatedTimeDesc();
        this.listView.setOnItemClickListener(new listView_OnItemClickListenerImpl());
    }
    /**
     * 从网络获取数据。
     */
    private void  getLineInfoOrderByCreatedTimeDesc() {
        //获取数据，数据类型ArrayList<Map<String,String>>
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllLineInfos), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYields",e.toString());
                result = e.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShowAllLinesInfoOrderByCreatedTimeActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                line_info_list = Utility.getAllLinesInfoOrderByCreatedTime(responseText);
                LogUtil.d("SubmitYields",line_info_list.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跟新listView
                        LineInfoAdapter lineInfoAdapter = new LineInfoAdapter(ShowAllLinesInfoOrderByCreatedTimeActivity.this,R.layout.lineinfo_item,line_info_list);
                        listView.setAdapter(lineInfoAdapter);
                    }
                });
            }
        });
    }
    private class LineInfoAdapter extends ArrayAdapter<V_Line_Info>{
        private int resourceId; //listView子项布局的ID
        public LineInfoAdapter(@NonNull Context context, int xmlItemResourceId, @NonNull List<V_Line_Info> objects) {
            super(context, xmlItemResourceId, objects);
            resourceId = xmlItemResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前项的V_Line_Info实例
            V_Line_Info v_line_info = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView textViewOfLineName = (TextView)view.findViewById(R.id.line_name);
            TextView textViewOfMonitor = (TextView)view.findViewById(R.id.monitor);
            textViewOfLineName.setText(v_line_info.getLine_name());
            textViewOfMonitor.setText(v_line_info.getMonitor());
            return view;
        }
    }

    private class listView_OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            V_Line_Info v_line_info = line_info_list==null?null:line_info_list.get(position);
            Intent intent = new Intent();
            intent.putExtra("line_name",v_line_info==null?"":v_line_info.getLine_name());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}