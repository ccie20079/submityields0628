package com.learning.utils;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.learning.Adapter.RVAdapterOfAlertDialog;
import com.learning.submityields0628.R;
import java.util.List;
/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/23
 * email:          ccie20079@126.com
 */
/**
 * 采用RecyclerView.Adapter适配器
 */
public class ShowAlertDialog {
    public static  void show(Context context, String title, List list, String btnConfirmText, DialogInterface.OnClickListener... submitListeners){
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.alertDialogStyle);
            dialog.setTitle(title);
            dialog.setCancelable(false);
            RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.recyclerview_dynamic_layout,null);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL_LIST));
            final RVAdapterOfAlertDialog rvAdapterOfAlertDialog
                    = new RVAdapterOfAlertDialog(list);
            recyclerView.setAdapter(rvAdapterOfAlertDialog);
            //设置布局方式
            dialog.setView(recyclerView);
            //dialog.setAdapter(emp_infoArrayAdapterOfAlertDialog, null);
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton(btnConfirmText,submitListeners.length==0?null:submitListeners[0]);
            dialog.show();
        }
        catch (Exception e){
            LogUtil.d(((Activity)context).getClass().getSimpleName(),e.toString());
        }
    }

}
