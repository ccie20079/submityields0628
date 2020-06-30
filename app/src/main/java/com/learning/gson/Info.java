package com.learning.gson;

import java.io.Serializable;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/28
 * email:          ccie20079@126.com
 */
public final class Info  {
    @BeanFileAnnotation(order=1,aliasName = "姓名")
    private String name;
    @BeanFileAnnotation(order = 2,aliasName = "年龄")
    private int age;
    @BeanFileAnnotation(order =3,aliasName = "体重")
    private double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Info(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }
}
