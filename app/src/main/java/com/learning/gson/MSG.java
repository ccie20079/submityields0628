package com.learning.gson;
/**
*	date: 2020-6-15
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class MSG {
	
	
	private transient int flag_int;
	private String msg;
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public int getFlag_int() {
		return flag_int;
	}
	public void setFlag_int(int flag_int) {
		this.flag_int = flag_int;
		this.flag = flag_int>0?true:false;		
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public MSG() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

