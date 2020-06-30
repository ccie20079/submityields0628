package com.learning.submityields0628;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.learning.gson.V_Specific_Process;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChooseSpecificProcessActivity extends BaseActivity {
    private ListView listViewOfSpecificProcess = null;
    List<V_Specific_Process> v_specific_processList = null;
    //Set集合 用于存储被选中的list索引
    private Set<Integer> the_specific_processes_seleted_set = null; //提交结束后，记得清零
    //被选中工序列表
    //private List<V_Specific_Process> the_specific_processes_selected_to_submit_list = null;
    private TextView textViewOfSeqOfSpecificProcessSelected = null;
    private Button btnAllDeselected = null;
    private Button btnTheSpecificProcessesSelectedToSubmit = null;
    private ChooseSpecificProcessAdapter chooseSpecificProcessAdapter = null;   //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_specific_process);
        listViewOfSpecificProcess = (ListView)super.findViewById(R.id.listViewOfSpecificProcess);
        textViewOfSeqOfSpecificProcessSelected = super.findViewById(R.id.textViewOfSeqOfSpecificProcessSelected);
        btnAllDeselected = (Button)super.findViewById(R.id.btnAllDeselected);
        btnTheSpecificProcessesSelectedToSubmit = (Button)super.findViewById(R.id.btnTheSpecificProcessesSelectedToSubmit);

        this.listViewOfSpecificProcess.setOnItemClickListener(new listViewOfSpecificProcess_OnItemClickListenerImpl());
        this.btnAllDeselected.setOnClickListener(new btnAllDeselected_OnClickListenerImpl());
        this.btnTheSpecificProcessesSelectedToSubmit.setOnClickListener(new btnTheSpecificProcessesSelectedToSubmit_OnClickListenerImpl());

        String products_name = getIntent().getStringExtra("products_name");
        //准备数据
        getTheSpecificProcessesOfThePN(products_name);

    }
    private void getTheSpecificProcessesOfThePN(String products_name) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("products_name",products_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetTheSpecificProcesses), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(getTheTAGOfTheCurrentInstance(),e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJsonStr= response.body().string();
                v_specific_processList= Utility.getTheSpecificProcessesByPN(responseJsonStr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            chooseSpecificProcessAdapter= new ChooseSpecificProcessAdapter(ChooseSpecificProcessActivity.this, R.layout.specific_process_item, v_specific_processList);
                            ChooseSpecificProcessActivity.this.listViewOfSpecificProcess.setAdapter(chooseSpecificProcessAdapter);
                        } catch (Exception ex) {
                            LogUtil.d("ChooseSpecificProcessActivity", ex.toString());
                        }
                    }
                });
            }
        });
    }

    /**
     *
     */
    private class listViewOfSpecificProcess_OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
//            // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//            ViewHolder holder = (ViewHolder) arg1.getTag();
            //view为 其中一个item的view
            ChooseSpecificProcessAdapter.ViewHolder viewHolder = (ChooseSpecificProcessAdapter.ViewHolder) itemView.getTag();
//            // 改变CheckBox的状态
//            holder.cb.toggle();
            viewHolder.checkboxOfSpecificProcess.toggle();  //切换
//            // 将CheckBox的选中状况记录下来
//            // 调整选定条目
            boolean isChecked = viewHolder.checkboxOfSpecificProcess.isChecked();
            if(the_specific_processes_seleted_set==null){
                the_specific_processes_seleted_set = new HashSet<Integer>();
            }
            if(isChecked){
                the_specific_processes_seleted_set.add(position);
            }else{
                the_specific_processes_seleted_set.remove(position);
            }
            String tempStr = "";
            //文字修改
            for(int seq:the_specific_processes_seleted_set){
                tempStr +=(seq+1) + ",";
            }
            tempStr = tempStr.substring(0,tempStr.length()-1);
            textViewOfSeqOfSpecificProcessSelected.setText("已选择的工序号："+ tempStr);
        }
    }

    /**
     * 清空已经选择的子项复选框
     */
    private class btnAllDeselected_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChooseSpecificProcessActivity.this.chooseSpecificProcessAdapter.notifyDataSetChanged();
            ChooseSpecificProcessActivity.this.textViewOfSeqOfSpecificProcessSelected.setText("已选择：");
            if(null ==the_specific_processes_seleted_set) return;
            ChooseSpecificProcessActivity.this.the_specific_processes_seleted_set.clear();
        }
    }

    private class btnTheSpecificProcessesSelectedToSubmit_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            //添加返回的工序。
            String theSpecificProcessesSeletedStr = "";
            for(int position:the_specific_processes_seleted_set){
                theSpecificProcessesSeletedStr += v_specific_processList.get(position).getSeq_p_c_record()+"_"+v_specific_processList.get(position).getSpecific_process()+";\r\n";
            }
            theSpecificProcessesSeletedStr = theSpecificProcessesSeletedStr.substring(0,theSpecificProcessesSeletedStr.length()-3);
            intent.putExtra("the_specific_processes",theSpecificProcessesSeletedStr);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}