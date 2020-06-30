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

import com.learning.gson.V_Emp_Name;
import com.learning.utils.BaseActivity;

import java.util.List;


public class ShowEmpNameOfPNActivity extends BaseActivity {
    private ListView listViewOfEmpNameInfo = null;
    private List<V_Emp_Name> v_emp_name_of_pn_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pn_emp_name_order_by_created_time);
        //获取SubmitYieldsActivity传来的list
        v_emp_name_of_pn_list=(List<V_Emp_Name>)getIntent().getSerializableExtra("emp_name_of_pn_serializable");
        this.listViewOfEmpNameInfo = (ListView)super.findViewById(R.id.listViewOfEmpNameInfo);
        EmpNameAdapter empNameAdapter = new EmpNameAdapter(ShowEmpNameOfPNActivity.this,R.layout.pn_emp_name,v_emp_name_of_pn_list);
        listViewOfEmpNameInfo.setAdapter(empNameAdapter);
        this.listViewOfEmpNameInfo.setOnItemClickListener(new listViewOfEmpNameInfos_OnClickListenerImpl());
    }

    private class EmpNameAdapter extends ArrayAdapter<V_Emp_Name> {
        private int resourceId; //listView子项布局的ID
        public EmpNameAdapter(@NonNull Context context, int xmlItemResourceId, @NonNull List<V_Emp_Name> objects) {
            super(context,xmlItemResourceId,objects);
            resourceId = xmlItemResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前项的V_Line_Info实例
            V_Emp_Name v_emp_name = getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView==null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.textViewOfLine_and_reportTeamName = (TextView)view.findViewById(R.id.textViewOfline_and_reportTeamName);
                viewHolder.textViewOfEmp_name = (TextView)view.findViewById(R.id.textViewOfEmp_name);
                //将ViewHolder存储在view中
                view.setTag(viewHolder);
            } else{
                view = convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.textViewOfLine_and_reportTeamName.setText(v_emp_name.getLine_name() + "\r\n"+v_emp_name.getReport_team_name());
            viewHolder.textViewOfEmp_name.setText("\r\n"+v_emp_name.getEmp_name());
            return view;
        }
        class ViewHolder{
            TextView textViewOfLine_and_reportTeamName;
            TextView textViewOfEmp_name;
        }
    }
    /**
     * 选择员工信息后返回给主界面。
     */
    private class listViewOfEmpNameInfos_OnClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            V_Emp_Name v_emp_name = v_emp_name_of_pn_list == null ? null : v_emp_name_of_pn_list.get(position);
            Intent intent = new Intent();
            intent.putExtra("emp_name", v_emp_name == null ? "" : v_emp_name.getEmp_name());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
