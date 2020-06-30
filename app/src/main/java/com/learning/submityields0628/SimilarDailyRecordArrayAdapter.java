package com.learning.submityields0628;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.learning.gson.V_Daily_Record;
import com.learning.gson.V_Specific_Process;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class SimilarDailyRecordArrayAdapter extends ArrayAdapter<V_Daily_Record> {
    private int xmlResourceId;
    public SimilarDailyRecordArrayAdapter(@NonNull Context context, int xmlResourceId, @NonNull List<V_Daily_Record> objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V_Daily_Record v_daily_record =getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView==null){
            //导入布局并赋值给convertView
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.seq_of_the_specific_process = (TextView) view.findViewById(R.id.seq_of_the_specific_process);
            viewHolder.line_name = (TextView)view.findViewById(R.id.line_name);
            viewHolder.station_name = (TextView)view.findViewById(R.id.station_name);
            viewHolder.specific_process = (TextView)view.findViewById(R.id.specific_process);
            viewHolder.amount_of_money = (TextView)view.findViewById(R.id.amount_of_money);
            viewHolder.time_str_of_the_client = (TextView)view.findViewById(R.id.time_str_of_the_client);

            view.setTag(viewHolder);
        }else{
           view = convertView;
           viewHolder = (ViewHolder) view.getTag();
        }
        //设置listView中的控件显示  为控件设置数据源
        viewHolder.seq_of_the_specific_process.setText(v_daily_record.getSeq_of_the_specific_process());
        viewHolder.line_name.setText(v_daily_record.getLine_name());
        viewHolder.station_name.setText(v_daily_record.getStation_name());
        viewHolder.specific_process.setText(v_daily_record.getSpecific_process());
        viewHolder.amount_of_money.setText(v_daily_record.getAmount_of_money());
        viewHolder.time_str_of_the_client.setText(v_daily_record.getTime_str_of_the_client());
        return view;
    }
    class ViewHolder{
        TextView seq_of_the_specific_process;
        TextView line_name;
        TextView station_name;
        TextView specific_process;
        TextView amount_of_money;
        TextView time_str_of_the_client;
    }
}
