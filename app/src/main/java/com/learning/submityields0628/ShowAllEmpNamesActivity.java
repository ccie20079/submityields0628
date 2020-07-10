package com.learning.submityields0628;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learning.gson.Emp_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ShowAllEmpNamesActivity extends BaseActivity {
    private ListView listViewOfEmpInfo = null;
    private List<Emp_Info> v_all_emp_info_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_emp_names_order_by_pinyin_asc);
        //获取SubmitYieldsActivity传来的list
        this.listViewOfEmpInfo = (ListView)super.findViewById(R.id.listViewOfEmpInfo);
        //获取数据
        getAllEmpInfosOrderByPINYINAsc();
        this.listViewOfEmpInfo.setOnItemClickListener(new listViewOfEmpNameInfos_OnClickListenerImpl());
    }

    private class EmpInfoAdapter extends ArrayAdapter<Emp_Info> {
        private int resourceId; //listView子项布局的ID
        public EmpInfoAdapter(@NonNull Context context, int xmlItemResourceId, @NonNull List<Emp_Info> objects) {
            super(context,xmlItemResourceId,objects);
            resourceId = xmlItemResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前项的V_Line_Info实例
            Emp_Info v_emp_info = getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView==null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.textViewOfEmpInfo_TeamName = (TextView)view.findViewById(R.id.textViewOfEmpInfo_TeamName);
                viewHolder.textViewOfEmpInfo_EmpName = (TextView)view.findViewById(R.id.textViewOfEmpInfo_EmpName);
                //将ViewHolder存储在view中
                view.setTag(viewHolder);
            } else{
                view = convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.textViewOfEmpInfo_TeamName.setText(v_emp_info.getTeam_name());
            viewHolder.textViewOfEmpInfo_EmpName.setText(v_emp_info.getEmp_name());
            return view;
        }
        class ViewHolder{
            TextView textViewOfEmpInfo_TeamName;
            TextView textViewOfEmpInfo_EmpName;
        }
    }
    /**
     * 选择员工信息后返回给主界面。
     */
    private class listViewOfEmpNameInfos_OnClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Emp_Info v_emp_info = v_all_emp_info_list == null ? null : v_all_emp_info_list.get(position);
            Intent intent = new Intent();
            intent.putExtra("emp_name", v_emp_info == null ? "" : v_emp_info.getEmp_name());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

       private void getAllEmpInfosOrderByPINYINAsc() {

        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllEmpInfos), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity: ",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                v_all_emp_info_list = Utility.getEmpInfos(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EmpInfoAdapter empNameAdapter = new EmpInfoAdapter(ShowAllEmpNamesActivity.this,R.layout.all_emps_item,v_all_emp_info_list);
                        listViewOfEmpInfo.setAdapter(empNameAdapter);
                    }
                });
            }
        });
    }
}
