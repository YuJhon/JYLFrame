package com.jyl.util.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * <p>功能描述：</br> json工具类</p>
 * @className  JsonUtil
 * @author  jiangyu
 * @date  2016年3月23日 下午2:32:08
 * @version  v1.0
 * @since v1.0
 */
public class JsonUtil {
    
    /**
     * <p> 功能描述：Map转换json(数组)</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:31:22
     * @param map 要转换的map对象
     * @return String 转换之后的json字符串
     * @since V1.0
     */
    public static String mapToJsonArray(Map<String,Object> map){
        JSONArray json = JSONArray.fromObject(map); 
        return json.toString();
    }
    
    /**
     * <p> 功能描述：Map转换json(对象)</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:32:36
     * @param map 要转换的map对象
     * @return String 转换之后的json字符串
     * @since V1.0
     */
    public static String mapToJsonObj(Map<String,Object> map){
        JSONObject json = JSONObject.fromObject(map); 
        return json.toString();
    }

    /**
     * <p> 功能描述：任意对象转换成json</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:33:31
     * @param object 要进行转换的object对象
     * @return String 转换成json字符串
     * @since V1.0
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String objectToJson(Object object) {
        StringBuilder json = new StringBuilder();
        if (object == null) {
            json.append("\"\"");
        } else if (object instanceof String) {
            json.append("\"").append((String) object).append("\"");
        } else if (object instanceof Integer) {
            json.append("\"").append(String.valueOf(object)).append("\"");
        } else if (object instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            json.append("\"").append(formatter.format((Date) object)).append(
                    "\"");
        } else if (object instanceof List<?>) {
            json.append("\"").append(listToJson((List<?>) object)).append("\"");
        } else if(object instanceof Map){
            json.append(mapToJsonObj((Map)object));
        }else{
            json.append(beanToJson(object));
        }
        return json.toString();
    }

    /**
     * <p> 功能描述：bean转成对象</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:34:33
     * @param bean 要进行转换的JavaBean对象
     * @return Strng 转换成Json的字符串
     * @since V1.0
     */
    public static String beanToJson(Object bean){
        JSONObject  jsonObject= JSONObject.fromObject(bean);
        return jsonObject.toString();
    }

    /**
     * <p> 功能描述： list 转成对象</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:35:26
     * @param list 要转换的List对象
     * @return String 转换成Json字符串
     * @since V1.0
     */
    public static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
    
    /**
     * <p> 功能描述：Json转list</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:36:24
     * @param jsonStr 要进行转换的Json字符串
     * @param clazz 转换的List中Bean的类型
     * @return List<T> json字符串转换后的List对象
     * @since V1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String jsonStr, Class<T> clazz) {
        JSONArray array = JSONArray.fromObject(jsonStr);
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            T t = (T)JSONObject.toBean(jsonObject, clazz);
            list.add(t);
        }
        return list;
    }
    
    /**
     * <p> 功能描述：Json转bean</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:38:03
     * @param jsonStr  要进行转换的Json字符串
     * @param clazz 转换的List中Bean的类型
     * @return <T> json字符串转换后的对象
     * @since V1.0
     */
    @SuppressWarnings("unchecked")
    public  static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        return (T) JSONObject.toBean(jsonObject, clazz);
    }
    
    
    /**
     * <p> 功能描述：JSON转成Map</p>
     * @author  tangjiabin
     * @date  2016年3月23日 下午2:39:15
     * @param jsonStr 要进行转换的Json字符串
     * @return Map json字符串转换之后的Map对象
     * @since V1.0
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> jsonToMap(String jsonStr){  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(jsonToMap(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }  
}
