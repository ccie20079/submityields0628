package com.learning.gson;

import androidx.annotation.NonNull;

import com.learning.utils.TimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
*	date: 2020-6-14
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class Emp_Info implements CharSequence{

	private int seq;

	private String emp_name;

	private String job_number;

	private Date inserted_time;

	private String team_name;

	private String monitor;

	private String mac;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getJob_number() {
		return job_number;
	}
	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}
	public Date getInserted_time() {
		return inserted_time;
	}
	public void setInserted_time(Date inserted_time) {
		this.inserted_time = inserted_time;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Emp_Info(String team_name, String emp_name, String mac) {
		super();
		this.emp_name = emp_name;
		this.team_name = team_name;
		this.mac = mac;
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public char charAt(int i) {
		return 0;
	}

	@NonNull
	@Override
	public CharSequence subSequence(int i, int i1) {
		return null;
	}

	@Override
	public String toString() {
		return "已注册：\r\n"
				+ team_name + "   班长：" + monitor + "\r\n"
				+ emp_name + "\r\n"
				+"MAC: "+ mac + "\r\n"
				+ TimeHelper.getTimeStr(inserted_time,"yyyy-MM-dd HH:mm:ss");
	}

}

