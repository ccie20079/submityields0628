package com.learning.gson;

/**
*	date: 2020-6-13
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
/**
 * 用于 标识 当天某员工的日产量记录
 * 
 * @author Administrator
 *
 */
public class V_Yields_Daily_Report {

	private String products_name;
	private String line_name;
	private String report_team_name;
	private String emp_name;
	private String summary_process;
	private String specific_process;
	private String man_hour;
	private String amount_of_money;
	private String inserted_time;
	
	
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
	public String getReport_team_name() {
		return report_team_name;
	}
	public void setReport_team_name(String report_team_name) {
		this.report_team_name = report_team_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
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
	public String getInserted_time() {
		return inserted_time;
	}
	public void setInserted_time(String inserted_time) {
		this.inserted_time = inserted_time;
	}


}
