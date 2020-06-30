package com.learning.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.learning.gson.BeanFileAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/28
 * email:          ccie20079@126.com
 */
public  class GetOrderedFiled {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static  List<Field> getOrderedField(Field[] fields){
        List<Field> fieldList =new ArrayList<>();
        for(Field f:fields){
            if(f.getAnnotation(BeanFileAnnotation.class)!=null){
                fieldList.add(f);   //未注解的忽略，不加入队列
            }
        }
        //这个比较顺序的方法依赖 于 java 1.8
        //fieldList.sort(Comparator.comparingInt(m->m.getAnnotation(BeanFileAnnotation.class).order()));
        Comparator<Field> comparator = new Comparator<Field>() {
            @Override
            public int compare(Field field, Field t1) {
                return (field.getAnnotation(BeanFileAnnotation.class).order()-t1.getAnnotation(BeanFileAnnotation.class).order());
            }
        };
        fieldList.sort(comparator);
        return fieldList;
    }
}
