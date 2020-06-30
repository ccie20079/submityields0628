package com.learning.gson;

/**
 * Package_name:   com.learning.gson
 * user:           Administrator
 * date:           2020/6/8
 * email:          ccie20079@126.com
 */
public class V_Products_Info_Recent {
    public String getG_SEQ_SPECIFIC_PROCESS() {
        return G_SEQ_SPECIFIC_PROCESS;
    }
    public void setG_SEQ_SPECIFIC_PROCESS(String g_SEQ_SPECIFIC_PROCESS) {
        G_SEQ_SPECIFIC_PROCESS = g_SEQ_SPECIFIC_PROCESS;
    }
    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }
    public void setPRODUCT_NAME(String pRODUCT_NAME) {
        PRODUCT_NAME = pRODUCT_NAME;
    }
    public String getSUMMARY_PROCESS() {
        return SUMMARY_PROCESS;
    }
    public void setSUMMARY_PROCESS(String sUMMARY_PROCESS) {
        SUMMARY_PROCESS = sUMMARY_PROCESS;
    }
    public String getSPECIFIC_PROCESS() {
        return SPECIFIC_PROCESS;
    }
    public void setSPECIFIC_PROCESS(String sPECIFIC_PROCESS) {
        SPECIFIC_PROCESS = sPECIFIC_PROCESS;
    }
    public String getINSERTED_TIME_OF_L_QUANTITIES() {
        return INSERTED_TIME_OF_L_QUANTITIES;
    }
    public void setINSERTED_TIME_OF_L_QUANTITIES(String iNSERTED_TIME_OF_L_QUANTITIES) {
        INSERTED_TIME_OF_L_QUANTITIES = iNSERTED_TIME_OF_L_QUANTITIES;
    }
    public String getSEQ_P_C() {
        return SEQ_P_C;
    }
    public void setSEQ_P_C(String sEQ_P_C) {
        SEQ_P_C = sEQ_P_C;
    }
    private String  G_SEQ_SPECIFIC_PROCESS;
    private String  PRODUCT_NAME;
    private String  SUMMARY_PROCESS;
    private String  SPECIFIC_PROCESS;
    private String  INSERTED_TIME_OF_L_QUANTITIES;
    private String  SEQ_P_C;

    public String getUrl_of_picture() {
        return url_of_picture;
    }

    public void setUrl_of_picture(String url_of_picture) {
        this.url_of_picture = url_of_picture;
    }

    private String url_of_picture;
    public V_Products_Info_Recent(String pRODUCT_NAME, String sUMMARY_PROCESS, String sPECIFIC_PROCESS) {
        super();
        PRODUCT_NAME = pRODUCT_NAME;
        SUMMARY_PROCESS = sUMMARY_PROCESS;
        SPECIFIC_PROCESS = sPECIFIC_PROCESS;
    }

    @Override
    public String toString() {
        return "V_Products_Info_Recent{" +
                "PRODUCT_NAME='" + PRODUCT_NAME + '\'' +
                ", SUMMARY_PROCESS='" + SUMMARY_PROCESS + '\'' +
                ", SPECIFIC_PROCESS='" + SPECIFIC_PROCESS + '\'' +
                '}';
    }
}
