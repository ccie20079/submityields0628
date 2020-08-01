package com.learning.utils;

import android.content.Context;
import android.util.AttributeSet;
/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/24
 * email:          ccie20079@126.com
 */
public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
      }
    /**
     * 欺骗系统,让系统认为FocusedTextView得到了焦点了
     */
    public boolean isFocused() {
        return true;
    }
}
