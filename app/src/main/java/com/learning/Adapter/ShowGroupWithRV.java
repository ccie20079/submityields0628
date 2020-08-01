package com.learning.Adapter;

/**
 * Package_name:   com.learning.Adapter
 * user:           草中斗鬼
 * date:           2020/7/29
 * email:          ccie20079@126.com
 */
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/**
 * 显示嵌套的列表，此处认为嵌套的列表，属于分组信息。
 */
public class ShowGroupWithRV  {
    public static <T extends HeaderRVAdapter.IGroupInfo> void showGroupWithRV(RecyclerView recyclerView, List<T> list){
        HeaderRVAdapter<T> headerRVAdapter = new HeaderRVAdapter<>(list);
        recyclerView.setAdapter(headerRVAdapter);
    }
}
