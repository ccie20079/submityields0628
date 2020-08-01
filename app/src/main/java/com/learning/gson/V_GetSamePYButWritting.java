package com.learning.gson;

import androidx.annotation.NonNull;

/**
*	date: 2020-7-10
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_GetSamePYButWritting implements CharSequence{
	 private String job_number;
	 private String name;
	 private String earliest_start_time;
	private String days_of_real_attendance;



	public String getDays_of_real_attendance() {
		return days_of_real_attendance;
	}
	public void setDays_of_real_attendance(String days_of_real_attendance) {
		this.days_of_real_attendance = days_of_real_attendance;
	}
	 
	public String getJob_number() {
		return job_number;
	}
	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEarliest_start_time() {
		return earliest_start_time;
	}
	public void setEarliest_start_time(String earliest_start_time) {
		this.earliest_start_time = earliest_start_time;
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
		return this.job_number+"\r\n     "
				+ this.name + "   出勤：" + this.days_of_real_attendance + "天"
				+"\r\n     最早到岗：" + this.earliest_start_time;
	}
}

