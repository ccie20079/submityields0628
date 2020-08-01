package com.learning.utils;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/26
 * email:          ccie20079@126.com
 */
public class MyPicture implements Serializable {
    public MyPicture(ImageView picture) {
        this.picture = picture;
    }

    private ImageView picture;
    public ImageView getPicture() {
        return picture;
    }
    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}
