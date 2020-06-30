package com.learning.gson;

import androidx.annotation.NonNull;

import com.learning.utils.StringHelper;

/**
*	date: 2020-6-22
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_GET_Similar_Odd_Job implements CharSequence {
	private String seq;
	private String particulars;
	private String labor_hours;
	private String quantities;
	private String amount_of_money;
	private String emp_name;
	private String inserted_time;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getInserted_time() {
		return inserted_time;
	}
	public void setInserted_time(String inserted_time) {
		this.inserted_time = inserted_time;
	}

	@Override
	public String toString() {
		return
				String.format("%s. %s\r\n%s金额:%s\r\n%s%s",seq,particulars, StringHelper.padLeft("",17,' '),amount_of_money,StringHelper.padLeft("",17,' '),inserted_time);
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public char charAt(int index) {
		return 0;
	}

	@NonNull
	@Override
	public CharSequence subSequence(int start, int end) {
		return null;
	}
}

