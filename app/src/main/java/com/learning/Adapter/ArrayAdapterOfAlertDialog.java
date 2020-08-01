package com.learning.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;

import com.learning.submityields0628.R;
import com.learning.utils.MyApplication;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class ArrayAdapterOfAlertDialog extends ArrayAdapter {
    // 2. 定义selectItem变量
    private int selectItem = -1;
    private int xmlResourceId;
    public ArrayAdapterOfAlertDialog(@NonNull Context context, int xmlResourceId, @NonNull List objects) {
        super(context, xmlResourceId, objects);
        this.xmlResourceId = xmlResourceId;
    }
    /**
     * 此构造方法使用默认的Layout,去适配。
     * @param context
     * @param objects
     */
    public ArrayAdapterOfAlertDialog(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.alert_dialog_listview_item_single_choice, objects);
        this.xmlResourceId = R.layout.alert_dialog_listview_item_single_choice;

    }
    /**
     *  Context由 MyApplication提供。
     * @param objects
     */
    public ArrayAdapterOfAlertDialog( @NonNull List objects) {
        super(MyApplication.getContext(), R.layout.alert_dialog_listview_item_single_choice, objects);
        this.xmlResourceId = R.layout.alert_dialog_listview_item_single_choice;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Object obj = getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView==null){
            //导入布局并赋值给convertView
            view = LayoutInflater.from(getContext()).inflate(xmlResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_of_alertDialog = (CheckedTextView) view.findViewById(R.id.tv_of_alertDialog);
            view.setTag(viewHolder);

        }else{
           view = convertView;
           viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_of_alertDialog.setText(obj.toString());
        if (position == selectItem) {
            viewHolder.tv_of_alertDialog.setBackgroundColor(R.color.skyblue);
        }
        else {
            viewHolder.tv_of_alertDialog.setBackgroundColor(android.R.color.transparent);
        }
        return view;
    }
    class ViewHolder{
       CheckedTextView tv_of_alertDialog;
    }
    // 3.listView的position传到变量selectItem中
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
}
