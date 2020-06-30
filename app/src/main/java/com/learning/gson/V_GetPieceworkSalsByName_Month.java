package com.learning.gson;

import java.io.Serializable;

/**
*	date: 2020-6-24
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_GetPieceworkSalsByName_Month implements Serializable {
	@BeanFileAnnotation(order = 1, aliasName = "seq" )
	private String seq;
	@BeanFileAnnotation(order=2,aliasName = "线体")
    private String line_name;
	@BeanFileAnnotation(order=3,aliasName = "品名")
    private String products_name;
	@BeanFileAnnotation(order=4,aliasName = "部位")
    private String summary_process;
	@BeanFileAnnotation(order=5,aliasName = "工序")
    private String specific_process;
	@BeanFileAnnotation(order=6,aliasName = "工价")
    private String amount_of_money;
	@BeanFileAnnotation(order=7,aliasName = "数量")
    private String quantities;
	@BeanFileAnnotation(order=8,aliasName = "金额")
    private double amount_of_money_sum;	//此属性暂时不用？
	@BeanFileAnnotation(order=9,aliasName = "保存时间")
    private String inserted_time;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getLine_name() {
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getProducts_name() {
		return products_name;
	}
	public void setProducts_name(String products_name) {
		this.products_name = products_name;
	}
	public String getSummary_process() {
		return summary_process;
	}
	public void setSummary_process(String summary_process) {
		this.summary_process = summary_process;
	}
	public String getSpecific_process() {
		return specific_process;
	}
	public void setSpecific_process(String specific_process) {
		this.specific_process = specific_process;
	}
	public String getAmount_of_money() {
		return amount_of_money;
	}
	public void setAmount_of_money(String amount_of_money) {
		this.amount_of_money = amount_of_money;
	}
	public String getQuantities() {
		return quantities;
	}
	public void setQuantities(String quantities) {
		this.quantities = quantities;
	}

	public double getAmount_of_money_sum() {
		return amount_of_money_sum;
	}

	public void setAmount_of_money_sum(double amount_of_money_sum) {
		this.amount_of_money_sum = amount_of_money_sum;
	}

	public String getInserted_time() {
		return inserted_time;
	}
	public void setInserted_time(String inserted_time) {
		this.inserted_time = inserted_time;
	}
}

