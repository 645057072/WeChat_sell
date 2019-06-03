package com.hlx.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    /**
     * 对象转换为String并进行json化操作
     * @param object
     * @return
     */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=new Gson();
        return gson.toJson(object);
    }
}
