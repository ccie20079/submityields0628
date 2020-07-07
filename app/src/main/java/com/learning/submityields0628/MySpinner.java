package com.learning.submityields0628;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

/**
 * Package_name:   com.learning.submityields0628
 * user:           Administrator
 * date:           2020/7/6
 * email:          ccie20079@126.com
 */
class MySpinner extends AppCompatSpinner {

    private static  final String TAG = "my_spinner";

    private int lastPosition = 0;


    public MySpinner(Context context) {
        super(context);
    }

    public MySpinner(Context context, int mode) {
        super(context, mode);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }
    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    //一个item选中的时候，总是会触发setSelection方法
    //在这个方法中，记录并检查上一次的selection position就行了


    @Override
    public void setSelection(int position, boolean animate) {
        super.setSelection(position, animate);
        if(position ==lastPosition){
            if(getOnItemClickListener()!=null){
                getOnItemSelectedListener().onItemSelected(this,getChildAt(position),position,0);
            }
            lastPosition  = position;
        }
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if(getOnItemSelectedListener()!=null){
            getOnItemSelectedListener().onItemSelected(this,getChildAt(position),position,0);
        }
        lastPosition = position;
    }

    public void initSpinner(){
        boolean isSpinnerFirst = true;
        


    }

}
