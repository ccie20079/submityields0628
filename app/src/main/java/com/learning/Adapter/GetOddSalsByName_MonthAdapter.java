package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class GetOddSalsByName_MonthAdapter extends ArrayAdapter<V_GetOddSalsByName_Month> {
    private int xmlResourceId;
    public GetOddSalsByName_MonthAdapter(@NonNull Context context, int xmlResourceId, @NonNull List<V_GetOddSalsByName_Month> objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V_GetOddSalsByName_Month v_getOddSalsByName_month =getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView==null){
            //导入布局并赋值给convertView
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_inserted_time = (TextView)view.findViewById(R.id.tv_inserted_time);
            viewHolder.tv_place = (TextView)view.findViewById(R.id.tv_place);
            viewHolder.tv_particulars = (TextView)view.findViewById(R.id.tv_particulars);
            viewHolder.tv_amount_of_money_sum = (TextView)view.findViewById(R.id.tv_amount_of_money_sum);

            view.setTag(viewHolder);
        }else{
           view = convertView;
           viewHolder = (ViewHolder) view.getTag();
        }
        //设置listView中的控件显示  为控件设置数据源
        viewHolder.tv_inserted_time.setText(v_getOddSalsByName_month.getInserted_time());
        viewHolder.tv_place.setText(v_getOddSalsByName_month.getPlace());
        viewHolder.tv_particulars.setText(v_getOddSalsByName_month.getParticulars());
        viewHolder.tv_amount_of_money_sum.setText(String.valueOf(v_getOddSalsByName_month.getAmount_of_money_sum()));

        return view;
    }
    class ViewHolder{
        TextView tv_inserted_time;
        TextView tv_place;
        TextView tv_particulars;
        TextView tv_amount_of_money_sum;
    }
}
