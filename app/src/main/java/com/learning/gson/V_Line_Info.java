package com.learning.gson;

import com.learning.utils.DateHelper;
import com.learning.utils.TimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/4
 * email:          ccie20079@126.com
 */
public class V_Line_Info {

    private Integer seq;
    private String line_name;
    private String monitor;
    private Date inserted_time;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getInserted_time() {
        return inserted_time;
    }

    public void setInserted_time(Date inserted_time) {
        this.inserted_time = inserted_time;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }
    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return this.line_name + " " + " M：" + monitor + " " + new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(this.inserted_time);
    }
}
