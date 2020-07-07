package com.learning.gson;

import java.sql.Blob;
import java.util.Date;


/**
*	date: 2020-7-7
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_GetAllStylesDistribution {
	  private String style_name;
	    private String line_name;
	    private Date inserted_time;
	    private int station_num;
	    private transient Blob picture;	//标记为transient将不被显示。
	    private String url_of_picture;
		public String getStyle_name() {
			return style_name;
		}
		public void setStyle_name(String style_name) {
			this.style_name = style_name;
		}
		public String getLine_name() {
			return line_name;
		}
		public void setLine_name(String line_name) {
			this.line_name = line_name;
		}
		public Date getInserted_time() {
			return inserted_time;
		}
		public void setInserted_time(Date inserted_time) {
			this.inserted_time = inserted_time;
		}
		public int getStation_num() {
			return station_num;
		}
		public void setStation_num(int station_num) {
			this.station_num = station_num;
		}
		public Blob getPicture() {
			return picture;
		}
		public void setPicture(Blob picture) {
			this.picture = picture;
		}
		public String getUrl_of_picture() {
			return url_of_picture;
		}
		public void setUrl_of_picture(String url_of_picture) {
			this.url_of_picture = url_of_picture;
		}
}

