package com.learning.utils;

import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void  sendOKHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     *
     * @param address
     * @param map   //传入的参数，要与action中接受的键值对一致。
     * @param callback
     */
    public static void sendOKHttpRequestWithPostMethod(String address,Map<String,String> map,okhttp3.Callback callback){
        //首先构造RequestBody对象存放待提交的数据
        FormBody.Builder formBuilder = new FormBody.Builder();
        Set<String> keySets = map.keySet();
        for (String key:keySets
             ) {
            String value = map.get(key);
            formBuilder.add(key,value);
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }
}
