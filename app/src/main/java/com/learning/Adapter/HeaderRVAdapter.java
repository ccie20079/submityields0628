package com.learning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.submityields0628.R;

import java.util.List;
import com.learning.Adapter.HeaderRVAdapter.IGroupInfo;
import com.learning.Adapter.SingleRecordOfGroupRVAdapter.ISingleRecordOfGroup;
/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/29
 * email:          ccie20079@126.com
 */
public class HeaderRVAdapter<T extends IGroupInfo> extends RecyclerView.Adapter<HeaderRVAdapter.ViewHolder> {
    private List list;
    private Context mContext;
    public HeaderRVAdapter(List<T> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(null==mContext){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_header_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T t = (T)list.get(position);
        holder.tv_header.setText(t.getHeader());
        //开始适配body;
        List<ISingleRecordOfGroup> list = (List<ISingleRecordOfGroup>) t.getDetailInfoList();
        SingleRecordOfGroupRVAdapter<? extends ISingleRecordOfGroup> adapter = new SingleRecordOfGroupRVAdapter(list);
        holder.recycler_view_content.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recycler_view_content.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView tv_header; //显示该对象的toString()方法
        RecyclerView recycler_view_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_header = (TextView)itemView.findViewById(R.id.tv_header);
            recycler_view_content= (RecyclerView)itemView.findViewById(R.id.recycler_view_content);
        }
    }
    public interface IGroupInfo {
        /**
         *
         * @return 返回组信息的头部
         */
        String getHeader();
        /**
         *
         * @return  组信息的详情
         */
        List<? extends SingleRecordOfGroupRVAdapter.ISingleRecordOfGroup> getDetailInfoList();
    }

}
