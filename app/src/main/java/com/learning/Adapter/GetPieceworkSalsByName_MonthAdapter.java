package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.gson.V_Specific_Process;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class GetPieceworkSalsByName_MonthAdapter extends ArrayAdapter<V_GetPieceworkSalsByName_Month> {
    private int xmlResourceId;
    public GetPieceworkSalsByName_MonthAdapter(@NonNull Context context, int xmlResourceId, @NonNull List<V_GetPieceworkSalsByName_Month> objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V_GetPieceworkSalsByName_Month v_getPieceworkSalsByName_month =getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView==null){
            //导入布局并赋值给convertView
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_inserted_time = (TextView)view.findViewById(R.id.tv_inserted_time);
            viewHolder.tv_line_name = (TextView)view.findViewById(R.id.tv_line_name);
            viewHolder.tv_products_name = (TextView)view.findViewById(R.id.tv_products_name);
            viewHolder.tv_summary_process = (TextView)view.findViewById(R.id.tv_summary_process);
            viewHolder.tv_specific_process = (TextView)view.findViewById(R.id.tv_specific_process);
            viewHolder.tv_amount_of_money_sum = (TextView)view.findViewById(R.id.tv_amount_of_money_sum);
            viewHolder.tv_quantities = (TextView)view.findViewById(R.id.tv_quantities);
            view.setTag(viewHolder);
        }else{
           view = convertView;
           viewHolder = (ViewHolder) view.getTag();
        }
        //设置listView中的控件显示  为控件设置数据源
        viewHolder.tv_inserted_time.setText(v_getPieceworkSalsByName_month.getInserted_time());
        viewHolder.tv_line_name.setText(v_getPieceworkSalsByName_month.getLine_name());
        viewHolder.tv_products_name.setText(v_getPieceworkSalsByName_month.getProducts_name());
        viewHolder.tv_summary_process.setText(v_getPieceworkSalsByName_month.getSummary_process());
        viewHolder.tv_specific_process.setText(v_getPieceworkSalsByName_month.getSpecific_process());
        double sum = v_getPieceworkSalsByName_month.getAmount_of_money_sum();
        if("合计".equals(v_getPieceworkSalsByName_month.getProducts_name())){
            viewHolder.tv_amount_of_money_sum.setText("合计："+String.valueOf(sum));
        }else {
            viewHolder.tv_amount_of_money_sum.setText(String.valueOf(sum));
        }
        String quantitiesStr = v_getPieceworkSalsByName_month.getQuantities();
        viewHolder.tv_quantities.setText(null==quantitiesStr?"":quantitiesStr+"件");
        return view;
    }
    class ViewHolder{
        TextView tv_inserted_time;
        TextView tv_line_name;
        TextView tv_products_name;
        TextView tv_summary_process;
        TextView tv_specific_process;
        //TextView tv_amount_of_money;
        TextView tv_quantities;
        TextView tv_amount_of_money_sum;
    }
}
