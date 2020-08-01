package com.learning.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.submityields0628.R;

/**
 * Created by Administrator on 2017/2/26.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    /*
     * 定义4个常量值，代表布局方向，分别是竖向线性布局、横向线性布局、竖向网格布局、横向网格布局
     */
    public static final int LINEAR_LAYOUT_ORIENTATION_VERTICAL = 0;
    public static final int LINEAR_LAYOUT_ORIENTATION_HORIZONTAL = 1;
    public static final int GRID_LAYOUT_ORIENTATION_VERTICAL = 2;
    public static final int GRID_LAYOUT_ORIENTATION_HORIZONTAL =  3;

    private int orientation = -1; // 当前的布局方向
    // 如果是网格布局我们要计算出每一行或者每一列（取决于布局方向）中的子项数目
    private int rawOrColumnSum = 0;
    // Drawable 对象用于绘制分隔线
    private Drawable myDivider = null;

    public MyItemDecoration(Context context, int orientation) {
        /* 这个构造方法用于处理线性布局传入的情况，我们要对myDivider对象进行初始化
         * （绘制的颜色和宽度等等）
         * R.drawable.my_list_divider 是我们自定义的一个drawable资源文件,我们通过
         * myContext来获取它
         */
        myDivider = context.getDrawable(R.drawable.my_list_divider);
        if(orientation == LinearLayoutManager.HORIZONTAL) {
            this.orientation = LINEAR_LAYOUT_ORIENTATION_HORIZONTAL;
        }else if(orientation == LinearLayoutManager.VERTICAL) {
            this.orientation = LINEAR_LAYOUT_ORIENTATION_VERTICAL;
        }

    }

    public MyItemDecoration(Context context, int orientation, int rawOrColumnSum) {
        // 这个构造方法用于处理网格布局传入的情况，原理同上
        myDivider =context.getDrawable(R.drawable.my_list_divider);

        if(orientation == GridLayoutManager.HORIZONTAL) {
            this.orientation = GRID_LAYOUT_ORIENTATION_HORIZONTAL;
        } else if(orientation == GridLayoutManager.VERTICAL) {
            this.orientation = GRID_LAYOUT_ORIENTATION_VERTICAL;
        }
        this.rawOrColumnSum = rawOrColumnSum;
    }

    // 在这个方法中。我们对布局方向进行判断，由此来调用正确的分隔线绘制方法
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(orientation == LINEAR_LAYOUT_ORIENTATION_HORIZONTAL ||
                orientation == LINEAR_LAYOUT_ORIENTATION_VERTICAL) {
            linearLayoutDrawItemDecoration(c, parent);
        } else if(orientation == GRID_LAYOUT_ORIENTATION_HORIZONTAL ||
                orientation == GRID_LAYOUT_ORIENTATION_VERTICAL) {
            gridLayoutItemDecoration(c, parent);
        }

    }

    /*
     * 当排布方式为线性布局的时候，绘制分割线的方法：
     */
    private void linearLayoutDrawItemDecoration(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount(); // 获取RecyclerView控件中的子控件总数
        int left, top, right, bottom;
        View child = parent.getChildAt(0);
        // 获取分割线的高度（把分割线看成一个小矩形）
        int drawableHeight = myDivider.getIntrinsicHeight();
        // 如果是竖直排布，那么分割线为横线
        if(orientation == LINEAR_LAYOUT_ORIENTATION_VERTICAL) {
            left = parent.getLeft();
            right = parent.getRight(); // 获取子控件开始 x 坐标和结束 x 坐标
            for (int i = 1; i < childCount; i++) {
                top = child.getBottom() - drawableHeight/2; // 获取开始点y坐标
                bottom = child.getBottom()  + drawableHeight/2; // 获取结束点y坐标
                myDivider.setBounds(left, top, right, bottom); // 设置绘制区域，下同
                myDivider.draw(canvas); // 在Canvas对象上绘制区域
                child = parent.getChildAt(i);
            }
            // 如果是水平排布，那么分割线为竖线
        } else if(orientation == LINEAR_LAYOUT_ORIENTATION_HORIZONTAL) {
            top = child.getTop();
            bottom = child.getBottom(); // 获取子控件的开始 y 坐标和结束 y 坐标
            for(int i = 1; i < childCount; i++) {
                left = child.getRight() - drawableHeight/2; // 获取开始点 x 坐标
                right = child.getRight() + drawableHeight/2; // 获取结束点 x 坐标
                myDivider.setBounds(left, top, right, bottom); // 设置绘制区域
                myDivider.draw(canvas);
                child = parent.getChildAt(i);
            }
        }
    }

    /*
     * 当排布方式为网格布局的时候，分割线的绘制方法：
     */
    private void gridLayoutItemDecoration(Canvas canvas, RecyclerView parent) {
        // 顺着布局方向上的要绘制的分割线条数
        int childCount = parent.getChildCount();
        int lineSum = childCount / rawOrColumnSum - 1;
        lineSum += childCount % rawOrColumnSum == 0 ? 0 : 1;
        // 获取分割线的高度（把分割线看成一个小矩形）
        int drawableHeight = myDivider.getIntrinsicHeight();
        int left, right, top, bottom;
        View child = parent.getChildAt(0);

        // 布局方向为竖直排布方式
        if(orientation == GRID_LAYOUT_ORIENTATION_VERTICAL) {
            left = parent.getLeft();
            right = parent.getRight();
            for(int i = 0; i < lineSum; i++) { // 循环用于绘制横向分割线
                child = parent.getChildAt(i*rawOrColumnSum);
                top = child.getBottom() - drawableHeight/2;
                bottom = child.getBottom() + drawableHeight/2;
                myDivider.setBounds(left, top, right, bottom);
                myDivider.draw(canvas);
            }
            top = parent.getTop();
            bottom = parent.getBottom();
            for(int i = 0; i < rawOrColumnSum-1; i++) { // 循环用于绘制竖向分割线
                child = parent.getChildAt(i);
                left = child.getRight() - drawableHeight/2;
                right = child.getRight() + drawableHeight/2;
                myDivider.setBounds(left, top, right, bottom);
                myDivider.draw(canvas);
            }
            // 布局方向为横向排布方式
        } else if(orientation == GRID_LAYOUT_ORIENTATION_HORIZONTAL) {
            top = parent.getTop();
            bottom = parent.getBottom();
            for(int i = 0; i <= lineSum; i++) { // 循环绘制竖向分割线
                child = parent.getChildAt(i*rawOrColumnSum);
                left = child.getRight() - drawableHeight/2;
                right = child.getRight() + drawableHeight/2;
                myDivider.setBounds(left, top, right, bottom);
                myDivider.draw(canvas);
            }
            left = parent.getLeft();
            right = parent.getRight();
            for(int i = 0; i < rawOrColumnSum; i++) { // 循环绘制横向分割线
                child = parent.getChildAt(i);
                top = child.getBottom() - drawableHeight/2;
                bottom = child.getBottom() + drawableHeight/2;
                myDivider.setBounds(left, top, right, bottom);
                myDivider.draw(canvas);
            }
        }
    }
}