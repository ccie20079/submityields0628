package com.learning.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/24
 * email:          ccie20079@126.com
 */
class MyAlertDialog extends AlertDialog {
    protected MyAlertDialog(@NonNull Context context) {
        super(context);
    }

    protected MyAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
