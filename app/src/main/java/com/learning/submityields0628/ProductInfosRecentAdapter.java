package com.learning.submityields0628;

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
import com.learning.gson.V_Products_Info_Recent;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/8
 * email:          ccie20079@126.com
 */
public class ProductInfosRecentAdapter extends RecyclerView.Adapter<ProductInfosRecentAdapter.ViewHolder> {
    private Context mContext;   //Glide.with(Context) 需要上下文参数。
    private List<V_Products_Info_Recent> mV_Products_Info_RecentList ;
    public ProductInfosRecentAdapter(List<V_Products_Info_Recent> mV_Products_Info_RecentList) {
        this.mV_Products_Info_RecentList = mV_Products_Info_RecentList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_info_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //整体布局定义监听器
        holder.productsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return The adapter position of the item if it still exists in the adapter.
                int position = holder.getAdapterPosition();
                V_Products_Info_Recent v_products_info_recent = mV_Products_Info_RecentList.get(position);
                if(v_products_info_recent==null) return;
                //启动SubmitYieldsActivity;
                Intent intent = new Intent(mContext,SubmitYieldsActivity.class);
                intent.putExtra("products_name",v_products_info_recent.getPRODUCT_NAME());
                mContext.startActivity(intent);
                if(Activity.class.isInstance(mContext)){
                    Activity activity = (Activity)mContext;
                    activity.finish();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        V_Products_Info_Recent v_products_info_recent = mV_Products_Info_RecentList.get(position);
        holder.textView_products_name.setText(v_products_info_recent.getPRODUCT_NAME());
        holder.textView_specificProcess.setText("1: " +v_products_info_recent.getSUMMARY_PROCESS() + " " + v_products_info_recent.getSPECIFIC_PROCESS());
        Glide.with(mContext).load(v_products_info_recent.getUrl_of_picture()).into(holder.imgViewOfTheProducts);    //将网络图片加载，并赋给imageView
    }

    @Override
    public int getItemCount() {
        return mV_Products_Info_RecentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View productsView;      //最外层的布局
        TextView textView_products_name;
        TextView textView_specificProcess;
        ImageView imgViewOfTheProducts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productsView = itemView;
            textView_products_name = (TextView) itemView.findViewById(R.id.textView_products_name);
            textView_specificProcess = (TextView) itemView.findViewById(R.id.textView_specificProcess);
            this.imgViewOfTheProducts = (ImageView)itemView.findViewById(R.id.imgViewOfTheProducts);
        }
    }
}
