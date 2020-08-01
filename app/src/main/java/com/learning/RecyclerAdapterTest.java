package com.learning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learning.Adapter.V_Style_Line_Adapter;
import com.learning.gson.V_GetAllStylesDistribution;
import com.learning.submityields0628.R;
import com.learning.submityields0628.V_Style_Station_Process_Activity;
import com.learning.utils.LogUtil;

import java.util.List;

/**
 * Package_name:   com.learning
 * user:           Administrator
 * date:           2020/7/27
 * email:          ccie20079@126.com
 */
public class RecyclerAdapterTest extends RecyclerView.Adapter<RecyclerAdapterTest.ViewHolder> {

    private Context mContext;   //Glide.with(Context) 需要上下文参数。
    private List<String> list ;
    public RecyclerAdapterTest(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerAdapterTest.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_simple_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTest.ViewHolder holder, int position) {
        String result = list.get(position);
        holder.tv_recycler_view.setText(result);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tv_recycler_view; //显示该对象的toString()方法
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_recycler_view = (TextView)itemView.findViewById(R.id.tv_recycler_view);
        }
    }

}
