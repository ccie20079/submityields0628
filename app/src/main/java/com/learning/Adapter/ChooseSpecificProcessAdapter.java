package com.learning.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.learning.gson.V_Specific_Process;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class ChooseSpecificProcessAdapter extends ArrayAdapter<V_Specific_Process> {
    private int xmlResourceId;
    public ChooseSpecificProcessAdapter(@NonNull Context context, int xmlResourceId, @NonNull List<V_Specific_Process> objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V_Specific_Process v_specific_process =getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView==null){
            //导入布局并赋值给convertView
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.checkboxOfSpecificProcess = (CheckBox)view.findViewById(R.id.checkboxOfSpecificProcess);
            viewHolder.textViewOfSeq_P_C_Record = (TextView)view.findViewById(R.id.textViewOfSeq_P_C_Record);
            viewHolder.textViewOfSummaryProcess = (TextView)view.findViewById(R.id.textViewOfSummaryProcess);
            viewHolder.textViewOfSpecificProcess = (TextView)view.findViewById(R.id.textViewOfSpecificProcess);
            viewHolder.textViewOfManHour = (TextView)view.findViewById(R.id.textViewOfManHour);
            viewHolder.textViewOfLabourCost = (TextView)view.findViewById(R.id.textViewOfLabourCost);

            view.setTag(viewHolder);
        }else{
           view = convertView;
           viewHolder = (ViewHolder) view.getTag();
        }
        //设置listView中的控件显示  为控件设置数据源
        //viewHolder.checkboxOfSpecificProcess.setChecked(v_specific_process.isChecked());
        viewHolder.textViewOfSummaryProcess.setText(v_specific_process.getSummary_process());
        viewHolder.textViewOfSpecificProcess.setText(v_specific_process.getSpecific_process());
        viewHolder.textViewOfManHour.setText(v_specific_process.getMan_hours()+" s");
        viewHolder.textViewOfLabourCost.setText(String.valueOf(v_specific_process.getLabour_cost()));
        viewHolder.textViewOfSeq_P_C_Record.setText(v_specific_process.getSeq_p_c_record());
        return view;
    }
    public class ViewHolder{
        public CheckBox checkboxOfSpecificProcess;
        TextView textViewOfSummaryProcess;
        TextView textViewOfSpecificProcess;
        TextView textViewOfManHour;
        TextView textViewOfLabourCost;
        TextView textViewOfSeq_P_C_Record;
    }
}
