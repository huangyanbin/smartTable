package com.bin.david.smarttable.utils;

import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Json转换Map-List集合辅助类
 * jsonArray 转换成List
 * JsonObject 转换成Map
 * Created by huangYanbin on 2018/2/8.
 */

public class JsonHelper {
    /**JSONObject*/
    public static final int JSON_TYPE_OBJECT = 1;
    /**JSONArray*/
    public static final int JSON_TYPE_ARRAY = 2;
    /**不是JSON格式的字符串*/
    public static final int JSON_TYPE_ERROR = 3;

/*
    @IntDef({JSON_TYPE_OBJECT,JSON_TYPE_ARRAY,JSON_TYPE_ERROR})
    public @interface JSON_TYPE {
    }
*/

    /**
     * json 转换成Map-List集合
     * @param json json
     * @return Map-List集合
     */
    public  static List<Object> jsonToMapList(String json){
        List<Object> mapList = null;
        try {
            if(getJSONType(json) == JSON_TYPE_OBJECT){
                JSONObject jsonObject = new JSONObject(json);
                Map<String,Object> objects = JsonHelper.reflect(jsonObject);
                mapList = new ArrayList<>();
                mapList.add(objects);
            }else if(getJSONType(json) == JSON_TYPE_ARRAY){
                JSONArray jsonArray = new JSONArray(json);
                mapList = JsonHelper.reflect(jsonArray);
            }else{
                Log.e("smartTable","json异常");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    /***
     *
     * 获取JSON类型
     * 判断规则
     */
    private static int getJSONType(String str) {
        if (TextUtils.isEmpty(str)) {
            return JSON_TYPE_ERROR;
        }
        char[] strChar = str.substring(0, 1).toCharArray();
        char firstChar = strChar[0];

        if (firstChar == '{') {
            return JSON_TYPE_OBJECT;
        }
        else if (firstChar == '[') {
            return JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE_ERROR;
        }
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * @param json
     * @return
     */
    public static Map<String, Object> reflect(JSONObject json){
        HashMap<String, Object> map = new LinkedHashMap<>();
        Iterator<String> keys = json.keys();
        try {
        for ( ;keys.hasNext();) {
            String key =  keys.next();
            Object o = json.get(key);
            if(o instanceof JSONArray)
                map.put( key, reflect((JSONArray) o));
            else if(o instanceof JSONObject)
                map.put(key, reflect((JSONObject) o));
            else
                map.put(key, o);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成Map-List集合
     * @param json
     * @return
     */
    public static List<Object> reflect(JSONArray json){
        List<Object> list = new ArrayList<>();
        try {
        for(int i = 0;i <json.length();i++){
            Object o = json.get(i);
            if(o instanceof JSONArray)
                list.add(reflect((JSONArray) o));
            else if(o instanceof JSONObject)
                list.add(reflect((JSONObject) o));
            else
                list.add(o);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
