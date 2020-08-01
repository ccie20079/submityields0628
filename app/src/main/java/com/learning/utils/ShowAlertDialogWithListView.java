package com.learning.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.learning.Adapter.ArrayAdapterOfAlertDialog;
import com.learning.submityields0628.R;

import java.util.List;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/21
 * email:          ccie20079@126.com
 */
public class ShowAlertDialogWithListView {
    /**
     *  alertDialog 的上下文，依赖于 Activity,需要指明是哪个Activity
     * @param context
     * @param title
     * @param list
     * @param submitListener
     */
    public static  void showWithItemClickLsn(Context context, String title, List list,AdapterView.OnItemClickListener AdapterView_OnItemClickListenerImpl, String btnConfirmText,DialogInterface.OnClickListener submitListener){
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.alertDialogStyle);
            dialog.setTitle(title);
            dialog.setCancelable(false);
            ArrayAdapterOfAlertDialog emp_infoArrayAdapterOfAlertDialog = new ArrayAdapterOfAlertDialog(context,R.layout.alert_dialog_listview_item_single_choice,list);
            ListView listView = (ListView) LayoutInflater.from(context).inflate(R.layout.listview_dynamic_layout,null);
            listView.setAdapter(emp_infoArrayAdapterOfAlertDialog);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setOnItemClickListener(AdapterView_OnItemClickListenerImpl);
            dialog.setView(listView);
            //dialog.setAdapter(emp_infoArrayAdapterOfAlertDialog, null);
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton(btnConfirmText,submitListener);
            dialog.show();
        }
        catch (Exception e){
            LogUtil.d(((Activity)context).getClass().getSimpleName(),e.toString());
        }
    }
    public static  void showWitoutItemClickLsn(Context context, String title, List list, String btnConfirmText,DialogInterface.OnClickListener... submitListener){
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.alertDialogStyle);
            dialog.setTitle(title);
            dialog.setCancelable(false);
            final ArrayAdapterOfAlertDialog emp_infoArrayAdapterOfAlertDialog = new ArrayAdapterOfAlertDialog(context,R.layout.alert_dialog_listview_item,list);
            ListView listView = (ListView) LayoutInflater.from(context).inflate(R.layout.listview_dynamic_layout,null);
            listView.setAdapter(emp_infoArrayAdapterOfAlertDialog);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    emp_infoArrayAdapterOfAlertDialog.setSelectItem(i);
                    emp_infoArrayAdapterOfAlertDialog.notifyDataSetChanged();
                }
            });
            dialog.setView(listView);
            //dialog.setAdapter(emp_infoArrayAdapterOfAlertDialog, null);
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton(btnConfirmText,submitListener[0]);
            dialog.show();
        }
        catch (Exception e){
            LogUtil.d(((Activity)context).getClass().getSimpleName(),e.toString());
        }
    }
}
