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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learning.gson.V_Team_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowAllTeamNameOrderByCreatedTimeActivity extends BaseActivity {
    List<V_Team_Info> team_info_list  = null;
    private ListView listView = null;
    private String result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_team_name_order_by_created_time);
        this.listView = (ListView)super.findViewById(R.id.listViewOfTeamInfo);
        getAllTeamNameOrderByCreatedTimeDesc();
        this.listView.setOnItemClickListener(new listView_OnItemClickListenerImpl());
    }

    /**
     * 从网络获取数据。
     */
    private void  getAllTeamNameOrderByCreatedTimeDesc() {
        //获取数据，数据类型ArrayList<Map<String,String>>
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllTeamInfos), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYields",e.toString());
                result = e.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShowAllTeamNameOrderByCreatedTimeActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                team_info_list = Utility.getAllTeamsInfoOrderByCreatedTime(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跟新listView
                        TeamNameAdapter teamNameAdapterAdapter = new TeamNameAdapter(ShowAllTeamNameOrderByCreatedTimeActivity.this,R.layout.team_name_item,team_info_list);
                        listView.setAdapter(teamNameAdapterAdapter);
                    }
                });
            }
        });
    }
    private class TeamNameAdapter extends ArrayAdapter<V_Team_Info>{
        private int resourceId; //listView子项布局的ID
        public TeamNameAdapter(@NonNull Context context, int xmlItemResourceId, @NonNull List<V_Team_Info> objects) {
            super(context, xmlItemResourceId, objects);
            resourceId = xmlItemResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前项的V_Line_Info实例
            V_Team_Info v_team_info = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView textViewOfTeamName = (TextView)view.findViewById(R.id.team_name);
            TextView textViewOfMonitor = (TextView)view.findViewById(R.id.monitor);
            textViewOfTeamName.setText(v_team_info.getTeam_name());
            textViewOfMonitor.setText(v_team_info.getMonitor());
            return view;
        }
    }

    private class listView_OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //注意 team_info_list有可能为空
            V_Team_Info v_team_info = team_info_list==null?null:team_info_list.get(position);
            Intent intent = new Intent();
            intent.putExtra("team_name",v_team_info==null?"":v_team_info.getTeam_name());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}