package com.yjh.cqa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

public class JsonUtil {
    private JsonUtil() {
    }

    public static <T> T getObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    public static <T> List<T> getObjectArray(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }

    public static Map<String, Object> getMap(String jsonString) {
        return JSON.parseObject(jsonString);
    }

    public static String getJsonString(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, JSON.DEFFAULT_DATE_FORMAT, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String getJsonString(Object obj, String dataFormat) {
        return JSON.toJSONStringWithDateFormat(obj, dataFormat, SerializerFeature.DisableCircularReferenceDetect);
    }
}