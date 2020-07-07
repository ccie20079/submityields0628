package com.learning.gson;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/30
 * email:          ccie20079@126.com
 */

import java.util.Date;

/**
 * 工单信息
 */
public class V_Products_Order {
    private String product_order;
    private String style_name;
    private String line_name;
    private int station_num;
    private Date insereted_time;
    private String url_of_picture;

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public int getStation_num() {
        return station_num;
    }

    public void setStation_num(int station_num) {
        this.station_num = station_num;
    }

    public String getUrl_of_picture() {
        return url_of_picture;
    }

    public void setUrl_of_picture(String url_of_picture) {
        this.url_of_picture = url_of_picture;
    }

    public String getProduct_order() {
        return product_order;
    }

    public void setProduct_order(String product_order) {
        this.product_order = product_order;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public Date getInsereted_time() {
        return insereted_time;
    }

    public void setInsereted_time(Date insereted_time) {
        this.insereted_time = insereted_time;
    }

    public V_Products_Order(String product_order, String style_name, Date insereted_time) {
        this.product_order = product_order;
        this.style_name = style_name;
        this.insereted_time = insereted_time;
    }
}
