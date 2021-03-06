package com.learning.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.learning.submityields0628.R;
import java.util.List;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/29
 * email:          ccie20079@126.com
 */
public class CommRVAdapter<T> extends RecyclerView.Adapter<CommRVAdapter.ViewHolder> {
    private List list;
    private Context mContext;
    public CommRVAdapter(List<T> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(null==mContext){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_simple_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T t = (T)list.get(position);
        holder.tv_recycler_view.setText(t.toString());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView tv_recycler_view; //显示该对象的toString()方法
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_recycler_view = (TextView)itemView.findViewById(R.id.tv_recycler_view);
        }
    }
}
