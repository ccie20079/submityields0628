package com.learning.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learning.gson.V_GetAllStylesDistribution;

import com.learning.submityields0628.R;
import com.learning.submityields0628.V_Style_Station_Process_Activity;
import com.learning.utils.LogUtil;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_style_distribution_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //整体布局定义监听器
        holder.styles_distribution_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return The adapter position of the item if it still exists in the adapter.
                int position = holder.getAdapterPosition();
                V_GetAllStylesDistribution v_getAllStylesDistribution = v_getAllStylesDistributionList.get(position);
                if(v_getAllStylesDistribution==null) return;
                //启动 //线体，款式布置详情页面
                Intent intent = new Intent(mContext, V_Style_Station_Process_Activity.class);
                intent.putExtra("line_name", v_getAllStylesDistribution.getLine_name());
                intent.putExtra("style_name",v_getAllStylesDistribution.getStyle_name());
                try{
                    mContext.startActivity(intent);
                    if(Activity.class.isInstance(mContext)){
                        Activity activity = (Activity)mContext;
                        activity.finish();
                    }
                }catch (Exception e){
                    LogUtil.d("***ERROR****",e.toString());
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        V_GetAllStylesDistribution v_getAllStylesDistribution = v_getAllStylesDistributionList.get(position);
        holder.tv_style_Name.setText(v_getAllStylesDistribution.getStyle_name());
        holder.tv_line_name.setText(v_getAllStylesDistribution.getLine_name());
        holder.tv_station_num.setText(String.valueOf(v_getAllStylesDistribution.getStation_num())+"站");
        Glide.with(mContext).load(v_getAllStylesDistribution.getUrl_of_picture()).into(holder.imgViewOfTheProducts);    //将网络图片加载，并赋给imageView
    }

    @Override
    public int getItemCount() {
        return v_getAllStylesDistributionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View styles_distribution_View;      //最外层的布局
        TextView tv_style_Name;  //款式名称
        TextView tv_line_name;
        TextView tv_station_num;
        ImageView imgViewOfTheProducts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            styles_distribution_View = itemView;
            tv_style_Name = (TextView) itemView.findViewById(R.id.tv_style_Name);
             tv_line_name = (TextView)itemView.findViewById(R.id.tv_line_name);
             tv_station_num= (TextView)itemView.findViewById(R.id.tv_station_num);
            this.imgViewOfTheProducts = (ImageView)itemView.findViewById(R.id.imgViewOfTheProducts);
        }
    }
}
