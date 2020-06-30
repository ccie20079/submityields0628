package com.learning.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/6
 * email:          ccie20079@126.com
 */
public class BitMapHelper {
    /**
     * 用于处理，从数据库返回的Blob对象。
     * @param blob
     * @return
     */
    public static Bitmap getBitMapByBLOB(Blob blob){
        try {
            long lengthOfBlob = blob.length();
            InputStream inputStream = blob.getBinaryStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
