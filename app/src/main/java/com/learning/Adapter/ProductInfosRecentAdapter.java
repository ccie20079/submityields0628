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
import com.learning.gson.V_Products_Order;
import com.learning.submityields0628.R;
import com.learning.submityields0628.SubmitYieldsActivity;

import java.util.List;

/**
 * Package_name:   com.learning.yieldssubmit200526
 * user:           Administrator
 * date:           2020/6/8
 * email:          ccie20079@126.com
 */
public class ProductInfosRecentAdapter extends RecyclerView.Adapter<ProductInfosRecentAdapter.ViewHolder> {
    private Context mContext;   //Glide.with(Context) 需要上下文参数。
    private List<V_Products_Order> v_products_orderList ;
    public ProductInfosRecentAdapter(List<V_Products_Order> v_products_orderList) {
        this.v_products_orderList = v_products_orderList;
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
        holder.product_Order_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return The adapter position of the item if it still exists in the adapter.
                int position = holder.getAdapterPosition();
                V_Products_Order v_products_order = v_products_orderList.get(position);
                if(v_products_order==null) return;
                //启动SubmitYieldsActivity;
                Intent intent = new Intent(mContext, SubmitYieldsActivity.class);
                intent.putExtra("product_order",v_products_order.getProduct_order());
                intent.putExtra("style_name",v_products_order.getStyle_name());
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

        V_Products_Order v_products_order = v_products_orderList.get(position);
        holder.tv_product_order.setText(v_products_order.getProduct_order());
        holder.tv_style_Name.setText(v_products_order.getStyle_name());
        holder.tv_line_name.setText(v_products_order.getLine_name());
        holder.tv_station_num.setText(String.valueOf(v_products_order.getStation_num())+"站");
        Glide.with(mContext).load(v_products_order.getUrl_of_picture()).into(holder.imgViewOfTheProducts);    //将网络图片加载，并赋给imageView
    }

    @Override
    public int getItemCount() {
        return v_products_orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View product_Order_View;      //最外层的布局
        TextView tv_product_order;
        TextView tv_style_Name;  //款式名称
        TextView tv_line_name;
        TextView tv_station_num;
        ImageView imgViewOfTheProducts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_Order_View = itemView;
            tv_product_order = (TextView) itemView.findViewById(R.id.tv_product_order);
            tv_style_Name = (TextView) itemView.findViewById(R.id.tv_style_Name);
             tv_line_name = (TextView)itemView.findViewById(R.id.tv_line_name);
             tv_station_num= (TextView)itemView.findViewById(R.id.tv_station_num);
            this.imgViewOfTheProducts = (ImageView)itemView.findViewById(R.id.imgViewOfTheProducts);
        }
    }
}
