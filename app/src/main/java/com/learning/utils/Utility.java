package com.learning.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learning.gson.MSG;
import com.learning.gson.V_Daily_Record;
import com.learning.gson.V_Emp_Info;
import com.learning.gson.V_Emp_Name;
import com.learning.gson.V_GET_Similar_Odd_Job;
import com.learning.gson.V_GetAllStylesDistribution;
import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;
import com.learning.gson.V_Line_Info;
import com.learning.gson.V_Products_Info_Recent;
import com.learning.gson.V_Products_Order;
import com.learning.gson.V_Specific_Process;
import com.learning.gson.V_Style_Station_Process;
import com.learning.gson.V_Team_Info;
import com.learning.gson.V_Yields_Daily_Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

public class Utility<T> {
//    /**
//     * 解析和处理服务器返回的省级数据
//     * @param response
//     * @return
//     */
//    public static boolean handleProvinceResponse(String response){
//        if(!TextUtils.isEmpty(response)){
//            try {
//                JSONArray allProvinces = new JSONArray(response);
//                for(int i = 0;i<allProvinces.length();i++){
//                    JSONObject provinceObject = allProvinces.getJSONObject(i);
//                    Province province = new Province();
//                    province.setProvinceName(provinceObject.getString("name"));
//                    province.setProvinceCode(provinceObject.getInt("id"));
//                    province.save();        //保存于本地
//                }
//                return true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * @param response
//     * @param provinceCode
//     * @return
//     */
//    public static boolean handleCityResponse(String response,int provinceCode){
//        if(!TextUtils.isEmpty(response)){
//            try {
//                JSONArray allCities = new JSONArray(response);
//                for(int i = 0;i<allCities.length();i++){
//                    JSONObject cityObject = allCities.getJSONObject(i);
//                    City city = new City();
//                    city.setCityCode(cityObject.getInt("id"));
//                    city.setCityName(cityObject.getString("name"));
//                    city.setProvinceCode(provinceCode);
//                    city.save();
//
//                }
//                return true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//    public static boolean handleCountyResponse(String response,int cityCode){
//        if(TextUtils.isEmpty(response)) return false;
//        try{
//            JSONArray allCounties = new JSONArray(response);
//            for(int i = 0;i<allCounties.length();i++){
//                JSONObject countyObject = allCounties.getJSONObject(i);
//                County county = new County();
//                county.setCityCode(cityCode);
//                county.setCountyName(countyObject.getString("name"));
//                county.setWeatherId(countyObject.getString("weather_id"));
//                county.save();
//            }
//            return true;
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 将返回的JSON数据解析成Weather实体类
//     * @param response
//     * @return
//     */
//    public static Weather handleWeatherResponse(String response){
//        try{
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
//            String weatherContent = jsonArray.getJSONObject(0).toString();
//            return new Gson().fromJson(weatherContent,Weather.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
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
    public static List<V_Emp_Info> getAllEmpInfosOrderByPinYinAsc(String jsonData) {
        Gson gson = new Gson();
        List<V_Emp_Info> v_emp_infoList = gson.fromJson(jsonData,new TypeToken<List<V_Emp_Info>>(){}.getType());
        return v_emp_infoList;
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

    public static MSG getMSG(String responseData) {
        Gson gson = new Gson();
        MSG msg = gson.fromJson(responseData,new TypeToken<MSG>(){}.getType());
        return msg;
    }

    public ArrayList<T> getListOfT(String jsonData){
        Gson gson = new Gson();
        ArrayList<T> resultListOfT = gson.fromJson(jsonData,new TypeToken<List<T>>(){}.getType());
        return resultListOfT;
    }
    public ArrayList<V_Daily_Record> get_V_Daily_Record_List(String jsonData){
        Gson gson = new Gson();
        ArrayList<V_Daily_Record> v_Daily_Record_List = gson.fromJson(jsonData,new TypeToken<List<V_Daily_Record>>(){}.getType());
        return v_Daily_Record_List;
    }
}
