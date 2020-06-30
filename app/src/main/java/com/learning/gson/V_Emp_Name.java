package com.learning.gson;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/6
 * email:          ccie20079@126.com
 */
public class V_Emp_Name implements Serializable {
    private String line_name;
    private String report_team_name;
    private String real_team_name;
    private String emp_name;
    private String monitor;

    public String getReport_team_name() {
        return report_team_name;
    }
    public void setReport_team_name(String report_team_name) {
        this.report_team_name = report_team_name;
    }
    public String getReal_team_name() {
        return real_team_name;
    }
    public void setReal_team_name(String real_team_name) {
        this.real_team_name = real_team_name;
    }
    public String getEmp_name() {
        return emp_name;
    }
    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getMonitor() {
        return monitor;
    }
    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }
    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }
    public V_Emp_Name(String report_team_name, String real_team_name, String monitor,String emp_name ) {
        super();
        this.report_team_name = report_team_name;
        this.real_team_name = real_team_name;
        this.emp_name = emp_name;
        this.monitor = monitor;
    }

    /**
     *
     * @return  用于返回组名称 和 班长的描述到前端显示。
     */
    public String getReportTeamNameAndMonitor(){
        return report_team_name + "\r\n" + monitor;
    }
}
