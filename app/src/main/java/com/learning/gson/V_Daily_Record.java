package com.learning.gson;

import androidx.annotation.NonNull;

import com.learning.utils.StringHelper;

/**
 * date: 2020-6-21 user: dongkui email: ccie20079@126.com
 * 
 */
public class V_Daily_Record implements CharSequence{
	private String products_name;
	private String line_name;
	private String station_name;
	private String seq_of_the_specific_process;
	private String summary_process;
	private String specific_process;
	private String man_hour;
	private String amount_of_money;
	private String emp_name;
	private String quantities;
	private String report_year_month_day;
	private String inserted_time;
	private String time_str_of_the_client;

	public String getProducts_name() {
		return products_name;
	}

	public void setProducts_name(String products_name) {
		this.products_name = products_name;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getSeq_of_the_specific_process() {
		return seq_of_the_specific_process;
	}

	public void setSeq_of_the_specific_process(String seq_of_the_specific_process) {
		this.seq_of_the_specific_process = seq_of_the_specific_process;
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

	public String getMan_hour() {
		return man_hour;
	}

	public void setMan_hour(String man_hour) {
		this.man_hour = man_hour;
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

	public String getQuantities() {
		return quantities;
	}

	public void setQuantities(String quantities) {
		this.quantities = quantities;
	}

	public String getReport_year_month_day() {
		return report_year_month_day;
	}

	public void setReport_year_month_day(String report_year_month_day) {
		this.report_year_month_day = report_year_month_day;
	}

	public String getInserted_time() {
		return inserted_time;
	}

	public void setInserted_time(String inserted_time) {
		this.inserted_time = inserted_time;
	}

	public String getTime_str_of_the_client() {
		return time_str_of_the_client;
	}

	public void setTime_str_of_the_client(String time_str_of_the_client) {
		this.time_str_of_the_client = time_str_of_the_client;
	}

	@Override
	public int length() {
		return toString().length();
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

	@Override
	public String toString() {
		return seq_of_the_specific_process  + ". "+line_name+"_"+station_name + "   "+ summary_process + "\r\n"
					+ StringHelper.padLeft("",(seq_of_the_specific_process  + ".  ").length(),' ') + specific_process + "\r\n"
					+ StringHelper.padLeft("",23,' ') + amount_of_money + " *"+ quantities+"件\r\n"
					+ StringHelper.padLeft("",15,' ')+ time_str_of_the_client;
	}
}
