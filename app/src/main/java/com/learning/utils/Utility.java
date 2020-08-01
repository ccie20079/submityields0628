package com.learning.utils;

import com.google.gson.Gson;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.learning.gson.Emp_Info;
import com.learning.gson.MSG;
import com.learning.gson.V_Daily_Record;
import com.learning.gson.V_Emp_Name;
import com.learning.gson.V_GET_Similar_Odd_Job;
import com.learning.gson.V_GetAllStylesDistribution;
import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.gson.V_GetSamePYButWritting;
import com.learning.gson.V_Line_Info;
import com.learning.gson.V_Products_Info_Recent;
import com.learning.gson.V_Products_Order;
import com.learning.gson.V_Specific_Process;
import com.learning.gson.V_Style_Station_Process;
import com.learning.gson.V_Team_Info;
import com.learning.gson.V_Yields_Daily_Report;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {

    public static List<V_Line_Info> getAllLinesInfoOrderByCreatedTime(String jsonData){
        Gson gson = new Gson();
        List<V_Line_Info> v_line_infoList =gson.fromJson(jsonData,new TypeToken<List<V_Line_Info>>(){}.getType());
        return v_line_infoList;
    }
    public static List<V_Team_Info> getAllTeamsInfoOrderByCreatedTime(String jsonData){
        Gson gson = new Gson();
        //有可能会返回空值
        List<V_Team_Info> v_team_infoList =gson.fromJson(jsonData,new TypeToken<List<V_Team_Info>>(){}.getType());
        return v_team_infoList;
    }

    public static List<V_Emp_Name> getAllEmpInfosRecentByCreatedTimeDesc(String jsonData) {
       Gson gson = new Gson();
       List<V_Emp_Name> v_emp_nameList = gson.fromJson(jsonData,new TypeToken<List<V_Emp_Name>>(){}.getType());
       return v_emp_nameList;
    }
    public static List<Emp_Info> getEmpInfos(String jsonData) {
        Gson gson = new Gson();
        List<Emp_Info> v_emp_infoList = gson.fromJson(jsonData,new TypeToken<List<Emp_Info>>(){}.getType());
        return v_emp_infoList;
    }
    public static Emp_Info getEmpInfo(String jsonData) {
        Gson gson = new Gson();
        Emp_Info v_emp_info = gson.fromJson(jsonData,new TypeToken<Emp_Info>(){}.getType());
        return v_emp_info;
    }
    public static List<V_Products_Info_Recent> getAllProductInfosRecentOrderByCreatedTimeDesc(String jsonData) {
        Gson gson = new Gson();
        List<V_Products_Info_Recent> v_products_info_recentList = gson.fromJson(jsonData,new TypeToken<List<V_Products_Info_Recent>>(){}.getType());
        return v_products_info_recentList;
    }
    public static List<V_Products_Order> getAllProductsOrderInfo(String jsonData) {
        Gson gson = new Gson();
        List<V_Products_Order> v_products_orderList = gson.fromJson(jsonData,new TypeToken<List<V_Products_Order>>(){}.getType());
        return v_products_orderList;
    }
    public static List<V_GetAllStylesDistribution> getAllStylesDistribution(String jsonData) {
        Gson gson = new Gson();
        List<V_GetAllStylesDistribution> v_getAllStylesDistributionList = gson.fromJson(jsonData,new TypeToken<List<V_GetAllStylesDistribution>>(){}.getType());
        return v_getAllStylesDistributionList;
    }
    public static List<V_Style_Station_Process> getProcessByLineAndStyleName(String jsonData) {
        Gson gson = new Gson();
        List<V_Style_Station_Process> v_style_station_processList = gson.fromJson(jsonData,new TypeToken<List<V_Style_Station_Process>>(){}.getType());
        return v_style_station_processList;
    }
    public static List<V_Specific_Process> getTheSpecificProcessesByPN(String jsonData){
        Gson gson = new Gson();
        List<V_Specific_Process> v_specific_processList = gson.fromJson(jsonData,new TypeToken<List<V_Specific_Process>>(){}.getType());
        return v_specific_processList;
    }
    public static List<V_Specific_Process> getProcessByPN_Line_StationAction(String jsonData){
        Gson gson = new Gson();
        List<V_Specific_Process> v_specific_processList = gson.fromJson(jsonData,new TypeToken<List<V_Specific_Process>>(){}.getType());
        return v_specific_processList;
    }
    public static List<V_Yields_Daily_Report> get_V_Yields_Daily_Report_List(String jsonData){
        Gson gson = new Gson();
        List<V_Yields_Daily_Report> v_yields_daily_reportList = gson.fromJson(jsonData,new TypeToken<List<V_Yields_Daily_Report>>(){}.getType());
        return v_yields_daily_reportList;
    }
    public static ArrayList<V_GetPieceworkSalsByName_Month> getPieceworkSalsByName_monthList(String jsonData){
        Gson gson = new Gson();
        ArrayList<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList = gson.fromJson(jsonData,new TypeToken<ArrayList<V_GetPieceworkSalsByName_Month>>(){}.getType());
        return v_getPieceworkSalsByName_monthList;
    }
    public static ArrayList<V_GetOddSalsByName_Month> getOddSalsByName_monthList(String jsonData){
        Gson gson = new Gson();
        ArrayList<V_GetOddSalsByName_Month> v_getOddSalsByName_MonthList = gson.fromJson(jsonData,new TypeToken<ArrayList<V_GetOddSalsByName_Month>>(){}.getType());
        return v_getOddSalsByName_MonthList;
    }
    /**
     * 获取相似零活相关的JSON解析数据。
     * @param string
     * @return
     */
    public static List<V_GET_Similar_Odd_Job> getSimilarOddJobList(String string) {
        Gson gson = new Gson();
        ArrayList<V_GET_Similar_Odd_Job> v_get_similar_odd_jobArrayList = gson.fromJson(string,new TypeToken<List<V_GET_Similar_Odd_Job>>(){}.getType());
        return v_get_similar_odd_jobArrayList;
    }

    /**
     * 获取姓名拼音相同的 考勤记录
     * @param string
     * @return
     */
    public static List<V_GetSamePYButWritting> getSamePYButWrittingList(String string) {
        Gson gson = new Gson();
        List<V_GetSamePYButWritting> v_getSamePYButWrittingList = gson.fromJson(string,new TypeToken<List<V_GetSamePYButWritting>>(){}.getType());
        return v_getSamePYButWrittingList;
    }
    public static MSG getMSG(String responseData) {
        Gson gson = new Gson();
        MSG msg = gson.fromJson(responseData,new TypeToken<MSG>(){}.getType());
        return msg;
    }

    /**
     * 返回为字符串的List
     * @param responseData
     * @return
     */
    public static List<String> getStringList(String responseData) {
        Gson gson = new Gson();
        List<String> list = gson.fromJson(responseData,new TypeToken<List<String>>(){}.getType());
        return list;
    }

    /**
     * 获取单一对象
     * @param json      其格式不为数组格式。
     * @param classOfT  解析为一个类
     * @param <T>
     * @return
     * @throws JsonParseException
     */
    public static <T> T getT(String json, Class<T> classOfT) throws JsonParseException {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 获取列表
     * @param jsonData  格式为数组
     * @param clazz     使用数组泛型解析。
     * @param <T>
     * @return
     */
    public static <T> List<T> getListOfT(String jsonData,Class<T[]> clazz)  {
        if("".equals(jsonData)) return new ArrayList<>();
        Gson gson = new Gson();
        T[] arrayOfT;
        try{
            arrayOfT =gson.fromJson(jsonData,clazz);
        }
        catch (Exception e){        //此处Catch  就不用
            LogUtil.d("Utility",e.toString());
            throw e;    //上抛异常
        }
        //Arrays$ArrayList which is an immutable list.
        //Arrays.asList();
        return  new ArrayList<>(Arrays.asList(arrayOfT));
    }
    /**
     * 返回数组类型，用于在AlertDialog中显示。
     * @param jsonData
     * @param clazz
     * @param <T>
     * @return
     * @throws JSONException
     */
    public static <T> T[]  getArrayOfT(String jsonData, Class<T[]> clazz) throws JSONException {
        Gson gson = new Gson();
        T[] arrayOfT=gson.fromJson(jsonData,clazz);
        return arrayOfT;
    }
    public ArrayList<V_Daily_Record> get_V_Daily_Record_List(String jsonData){
        Gson gson = new Gson();
        ArrayList<V_Daily_Record> v_Daily_Record_List = gson.fromJson(jsonData,new TypeToken<List<V_Daily_Record>>(){}.getType());
        return v_Daily_Record_List;
    }
}
