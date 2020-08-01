package com.learning.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/28
 * email:          ccie20079@126.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanFileAnnotation {
    /**
     * @return 该属性的顺序
     */
    int order();

    /**
     * @return  返回字段的别名
     */
    String aliasName();
}
