package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.submityields0628.R;
import com.learning.Adapter.SingleRecordOfGroupRVAdapter.ISingleRecordOfGroup;
import java.util.List;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/29
 * email:          ccie20079@126.com
 */
public class SingleRecordOfGroupRVAdapter<T extends ISingleRecordOfGroup> extends RecyclerView.Adapter<SingleRecordOfGroupRVAdapter.ViewHolder> {
    private List list;
    private Context mContext;
    public SingleRecordOfGroupRVAdapter(List<T> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(null==mContext){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_single_record_of_group_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T t = (T)list.get(position);
        holder.tv_single_record_of_group.setText(t.getSingleRecord());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView tv_single_record_of_group;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_single_record_of_group = (TextView)itemView.findViewById(R.id.tv_single_record_of_group);
        }
    }

    /**
     *
     */
    public interface ISingleRecordOfGroup{
        /**
         * 获得组中的一行。
         * @return
         */
        String getSingleRecord();
    }
}
