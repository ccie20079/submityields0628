package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.gson.V_Specific_Process;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/7
 * email:          ccie20079@126.com
 */
public class V_Specific_Process_Adapter extends RecyclerView.Adapter<V_Specific_Process_Adapter.ViewHolder> {
    private Context mContext;
    private List<V_Specific_Process> v_specific_processList;
    public V_Specific_Process_Adapter(List<V_Specific_Process> v_specific_processList) {
        this.v_specific_processList = v_specific_processList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_specific_process_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        V_Specific_Process v_specific_process = v_specific_processList.get(position);
        holder.tv_seq_p_c_record.setText(v_specific_process.getSeq_p_c_record()+".");
        holder.tv_summary_process.setText(v_specific_process.getSummary_process());
        holder.tv_specific_process.setText(v_specific_process.getSpecific_process());
        holder.tv_man_hours.setText(String.valueOf(v_specific_process.getMan_hours()+" S "));
        holder.tv_labour_cost.setText(String.valueOf(v_specific_process.getLabour_cost()));
    }

    @Override
    public int getItemCount() {
        return v_specific_processList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View v_Specific_Process_View;
        TextView tv_seq_p_c_record;
        TextView tv_summary_process;
        TextView tv_specific_process;
        TextView tv_man_hours;
        TextView tv_labour_cost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v_Specific_Process_View =itemView;
            tv_seq_p_c_record = (TextView)itemView.findViewById(R.id.tv_seq_p_c_record);
            tv_summary_process = (TextView)itemView.findViewById(R.id.tv_summary_process);
            tv_specific_process = (TextView)itemView.findViewById(R.id.tv_specific_process);
            tv_man_hours = (TextView)itemView.findViewById(R.id.tv_man_hours);
            tv_labour_cost = (TextView)itemView.findViewById(R.id.tv_labour_cost);
        }
    }
}
