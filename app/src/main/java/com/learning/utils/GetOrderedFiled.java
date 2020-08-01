package com.learning.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.learning.gson.BeanFileAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            public int compare(Field t1, Field t2) {
                return (t1.getAnnotation(BeanFileAnnotation.class).order()-t2.getAnnotation(BeanFileAnnotation.class).order());
            }
        };
        fieldList.sort(comparator);
        return fieldList;
    }
    public static String aliasName(Field field){
        String fieldName = field.getDeclaredAnnotation(BeanFileAnnotation.class).aliasName();
        return "".equals(fieldName) ? field.getName() : fieldName;
    }

    /**
     *
     * @param field
     * @param t 泛型T的实例
     * @param <T>
     * @return  对象t的某个属性(字段)的值
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> Object getValueOfField(Field field,T t) throws NoSuchMethodException,  IllegalAccessException, InvocationTargetException {
        String fieldName = field.getName();
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getMethod = t.getClass().getMethod(getMethodName);
        Object value = getMethod.invoke(t, new Object[]{});
        return value;
    }
}
