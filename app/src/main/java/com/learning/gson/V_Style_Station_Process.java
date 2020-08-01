package com.learning.gson;


import com.learning.Adapter.HeaderRVAdapter;
import com.learning.Adapter.SingleRecordOfGroupRVAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
*	date: 2020-7-6
*	user: dongkui 
*	email: ccie20079@126.com
*	
*/
public class V_Style_Station_Process implements HeaderRVAdapter.IGroupInfo {
	private String line_name;
	private String style_name;
	private String station_name;
	private Date max_inserted_time;
	private String seq_of_p_c_conn_str;
	private List<V_Specific_Process> v_specific_process_list = new ArrayList<>();
	public List<V_Specific_Process> getV_specific_process_list() {
		return v_specific_process_list;
	}
	public void setV_specific_process_list(List<V_Specific_Process> v_specific_process_list) {
		this.v_specific_process_list = v_specific_process_list;
	}
	public V_Style_Station_Process() {
		super();
	}
	public String getLine_name() {
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getStyle_name() {
		return style_name;
	}
	public void setStyle_name(String style_name) {
		this.style_name = style_name;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public Date getMax_inserted_time() {
		return max_inserted_time;
	}
	public void setMax_inserted_time(Date max_inserted_time) {
		this.max_inserted_time = max_inserted_time;
	}
	public String getSeq_of_p_c_conn_str() {
		return seq_of_p_c_conn_str;
	}
	public void setSeq_of_p_c_conn_str(String seq_of_p_c_conn_str) {
		this.seq_of_p_c_conn_str = seq_of_p_c_conn_str;
	}
	@Override
	public String toString() {
		return "工位： "+station_name;
	}
	@Override
	public String getHeader() {
		return "工位： "+station_name;
	}

	@Override
	public List<? extends SingleRecordOfGroupRVAdapter.ISingleRecordOfGroup> getDetailInfoList() {
		return (List<? extends SingleRecordOfGroupRVAdapter.ISingleRecordOfGroup>) this.v_specific_process_list;
	}
}

