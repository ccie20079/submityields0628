package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.gson.V_Specific_Process;
import com.learning.gson.V_Style_Station_Process;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/7
 * email:          ccie20079@126.com
 */
public class V_Style_Station_Process_Adapter extends ArrayAdapter<V_Style_Station_Process> {
    private int xmlResourceId;
    public V_Style_Station_Process_Adapter(@NonNull Context context,  int xmlResourceId, @NonNull List<V_Style_Station_Process> objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        V_Style_Station_Process v_style_station_process = getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        //第一次设置View
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_station_name = view.findViewById(R.id.tv_station_name);
            viewHolder.recyclerView_v_specific_processes = view.findViewById(R.id.recyclerView_v_specific_processes);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //赋值
        viewHolder.tv_station_name.setText("工站： " + v_style_station_process.getStation_name());
        //来一个RecyclerViewAdapter
        List<V_Specific_Process> v_specific_processList = v_style_station_process.getV_specific_process_list();
        //此由session.createQuery(HQL).list()产生，有可能为空
//        if(v_specific_processList==null){
//            return view;
//        }
        V_Specific_Process_Adapter v_specific_process_adapter = new V_Specific_Process_Adapter(v_specific_processList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        viewHolder.recyclerView_v_specific_processes.setLayoutManager(linearLayoutManager);
        viewHolder.recyclerView_v_specific_processes.setAdapter(v_specific_process_adapter);
        return view;
    }
    class ViewHolder{
        TextView tv_station_name;
        //放置一个 RecyclerView
        RecyclerView recyclerView_v_specific_processes;
    }
}
