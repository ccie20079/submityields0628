package com.learning.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/28
 * email:          ccie20079@126.com
 */
public class CharactersHelper {
    /**
     *
     * @return 获取屏幕宽度
     */
    public static float getWidthWithPixsUnit(){
        Resources resources = MyApplication.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width3 = dm.widthPixels;
        int height3 = dm.heightPixels;
        return width3;
    }

    /**
     * 把pix值转换为sp
     *
     * @return
     */
    public static float px2sp(float pixValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return  pixValue / fontScale + 0.5f;
    }

    /**
     *
     * @return 返回屏幕宽度
     */
    public static float getWidthWithSpUnit(){
        return px2sp(getWidthWithPixsUnit());
    }

    /**
     *
     * @return textSize为14sp时，返回屏幕宽容纳的字符个数。
     */
    public static int getDefaultCharactersOfWidth(){
        return (int) (px2sp(getWidthWithPixsUnit())/14.0);
    }

    /**
     *
     * @param marginLeftAndRight  距离左右的像素和
     * @return
     */
    public static int getDefaultCharactersOfWidth(float marginLeftAndRight){
        float remainWidth = getWidthWithPixsUnit() -marginLeftAndRight;
        float remainWidthWithSp = px2sp(remainWidth);
        int countsOfCharacters =(int)(remainWidthWithSp/14.0);
        return countsOfCharacters;
    }
}
