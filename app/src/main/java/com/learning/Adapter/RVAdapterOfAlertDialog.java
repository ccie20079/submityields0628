package com.learning.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.learning.submityields0628.R;
import java.util.List;
/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/7
 * email:          ccie20079@126.com
 */
public class RVAdapterOfAlertDialog extends RecyclerView.Adapter<RVAdapterOfAlertDialog.ViewHolder> {
    private Context mContext;   //用于Toast
    private List list;
    private PositionOfRV positionOfRV;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View theItemView;   //存储整体view
        TextView tv_of_alertDialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            theItemView =itemView;
            tv_of_alertDialog = (TextView) itemView.findViewById(R.id.tv_of_alertDialog);
        }

    }
    /**
     *
     * @param list
     * @param positionOfRV
     */
    public RVAdapterOfAlertDialog(List list, PositionOfRV positionOfRV) {
       this.list = list;
       this.positionOfRV = positionOfRV;
    }
    public RVAdapterOfAlertDialog(List list) {
        this.list = list;
        this.positionOfRV = positionOfRV;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
         //保存上下文    parent即： recyclerView
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_dialog_recyclerview_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);   //构造方法
        if(this.positionOfRV!=null){
            holder.tv_of_alertDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RVAdapterOfAlertDialog.this.positionOfRV.setSelected_position(holder.getAdapterPosition());
                }
            });
        }

        return holder;
    }
    /**
     * 每一个子项被滚动到屏幕内的时候执行，这里通过position参数得到当前项的实例。
     * @param holder
     * @param position
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object obj  = list.get(position);
        holder.tv_of_alertDialog.setText(obj.toString());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
