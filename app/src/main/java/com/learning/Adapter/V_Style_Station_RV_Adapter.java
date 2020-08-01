package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.gson.V_Specific_Process;
import com.learning.gson.V_Style_Station_Process;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/27
 * email:          ccie20079@126.com
 */
public class V_Style_Station_RV_Adapter extends RecyclerView.Adapter<V_Style_Station_RV_Adapter.ViewHolder> {
    private List<V_Style_Station_Process> list;
    private Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.v_style_station_process_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        V_Style_Station_Process itemData = list.get(position);
        List<V_Specific_Process> v_specific_processList = itemData.getV_specific_process_list();
        holder.tv_station_name.setText("工站： "+itemData.getStation_name());
        //绑定数据。在此适配详细信息。
        V_Specific_Process_Adapter v_specific_process_adapter = new V_Specific_Process_Adapter(v_specific_processList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.recyclerView_v_specific_processes.setLayoutManager(linearLayoutManager);
        holder.recyclerView_v_specific_processes.setAdapter(v_specific_process_adapter);
    }

    public V_Style_Station_RV_Adapter(List<V_Style_Station_Process> v_style_station_processList) {
        this.list = v_style_station_processList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView tv_station_name;
        RecyclerView recyclerView_v_specific_processes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_station_name = (TextView)itemView.findViewById(R.id.tv_station_name);
            recyclerView_v_specific_processes = (RecyclerView)itemView.findViewById(R.id.recyclerView_v_specific_processes);
        }
    }
}
