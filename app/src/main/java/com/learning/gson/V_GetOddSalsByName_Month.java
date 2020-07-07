package com.learning.gson;

import java.io.Serializable;

/**
*	date: 2020-6-24
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_GetOddSalsByName_Month implements Serializable {
	@BeanFileAnnotation(order = 1,aliasName = "")
	private String seq;
	@BeanFileAnnotation(order = 2,aliasName = "地点")
    private String place;
	@BeanFileAnnotation(order = 3,aliasName = "事由")
    private String particulars;
	@BeanFileAnnotation(order = 4,aliasName = "工作时长(小时)")
    private String labor_hours;
	@BeanFileAnnotation(order = 5,aliasName = "数量")
     private String quantities;
	@BeanFileAnnotation(order = 6,aliasName = "金额")
	private double amount_of_money_sum;
	@BeanFileAnnotation(order = 7,aliasName = "保存时间")
    private String inserted_time;
	//@BeanFileAnnotation(order = 6,aliasName = "金额")	未注解，不写入Excel
	private String amount_of_money;


	public double getAmount_of_money_sum() {
		return amount_of_money_sum;
	}

	public void setAmount_of_money_sum(double amount_of_money_sum) {
		this.amount_of_money_sum = amount_of_money_sum;
	}

	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getLabor_hours() {
		return labor_hours;
	}
	public void setLabor_hours(String labor_hours) {
		this.labor_hours = labor_hours;
	}
	public String getQuantities() {
		return quantities;
	}
	public void setQuantities(String quantities) {
		this.quantities = quantities;
	}
	public String getAmount_of_money() {
		return amount_of_money;
	}
	public void setAmount_of_money(String amount_of_money) {
		this.amount_of_money = amount_of_money;
	}
	public String getInserted_time() {
		return inserted_time;
	}
	public void setInserted_time(String inserted_time) {
		this.inserted_time = inserted_time;
	}
    
    
}

