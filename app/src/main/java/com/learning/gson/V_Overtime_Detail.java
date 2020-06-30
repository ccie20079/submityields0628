package com.learning.gson;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/29
 * email:          ccie20079@126.com
 */
public class V_Overtime_Detail {
    @BeanFileAnnotation(order = 1,aliasName = "描述")
    private String description;
    @BeanFileAnnotation(order = 2,aliasName = "金额")
    private double amountOfMoney;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public V_Overtime_Detail(String description, double amountOfMoney) {
        this.description = description;
        this.amountOfMoney = amountOfMoney;
    }
}
