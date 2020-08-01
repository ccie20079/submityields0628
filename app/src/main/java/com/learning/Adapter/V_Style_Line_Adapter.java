package com.learning.Adapter;

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
import com.learning.gson.V_GetAllStylesDistribution;

import com.learning.submityields0628.R;
import com.learning.submityields0628.V_Style_Station_Process_Activity;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/8
 * email:          ccie20079@126.com
 */

/**
 *
 */
public class V_Style_Line_Adapter extends RecyclerView.Adapter<V_Style_Line_Adapter.ViewHolder> {
    private Context mContext;   //Glide.with(Context) 需要上下文参数。
    private List<V_GetAllStylesDistribution> v_getAllStylesDistributionList ;
    public V_Style_Line_Adapter(List<V_GetAllStylesDistribution> v_getAllStylesDistributionList) {
        this.v_getAllStylesDistributionList = v_getAllStylesDistributionList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.line_style_distribution_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //整体布局定义监听器
        holder.styles_distribution_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return The adapter position of the item if it still exists in the adapter.
                int position = holder.getAdapterPosition();
                V_GetAllStylesDistribution v_getAllStylesDistribution = v_getAllStylesDistributionList.get(position);
                if(v_getAllStylesDistribution==null) return;
                if(null==v_getAllStylesDistribution.getLine_name()) return;
                //启动 //线体，款式布置详情页面
                Intent intent = new Intent(mContext, V_Style_Station_Process_Activity.class);
                intent.putExtra("line_name", v_getAllStylesDistribution.getLine_name());
                intent.putExtra("style_name",v_getAllStylesDistribution.getStyle_name());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        V_GetAllStylesDistribution v_getAllStylesDistribution = v_getAllStylesDistributionList.get(position);
        holder.tv_v_getAllStylesDistribution.setText(v_getAllStylesDistribution.toString());
        Glide.with(mContext).load(v_getAllStylesDistribution.getBytesOfPicture()).into(holder.imgViewOfTheProducts);    //将网络图片加载，并赋给imageView
    }
    @Override
    public int getItemCount() {
        return v_getAllStylesDistributionList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView styles_distribution_cardView;      //最外层的布局
        TextView tv_v_getAllStylesDistribution; //显示该对象的toString()方法
        ImageView imgViewOfTheProducts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            styles_distribution_cardView = (CardView)itemView;
            tv_v_getAllStylesDistribution = (TextView)itemView.findViewById(R.id.tv_v_getAllStylesDistribution);
            this.imgViewOfTheProducts = (ImageView)itemView.findViewById(R.id.imgViewOfTheProducts);
        }
    }
}
