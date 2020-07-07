package com.learning.gson;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/12
 * email:          ccie20079@126.com
 */
public class V_Specific_Process {


    private String seq_p_c_record;
    private String summary_process;
    private String specific_process;
    private double man_hours;
    private double labour_cost;

    public String getSeq_p_c_record() {
        return seq_p_c_record;
    }

    public void setSeq_p_c_record(String seq_p_c_record) {
        this.seq_p_c_record = seq_p_c_record;
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

    public double getMan_hours() {
        return man_hours;
    }

    public void setMan_hours(double man_hours) {
        this.man_hours = man_hours;
    }

    public double getLabour_cost() {
        return labour_cost;
    }

    public void setLabour_cost(double labour_cost) {
        this.labour_cost = labour_cost;
    }
}
