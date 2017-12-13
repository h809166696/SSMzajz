package com.hj.utils.common;
import com.alibaba.fastjson.*;
/**
 * Created by hongjin on 2017/11/18.
 */
public class JsonTools {
    /**
     * 将字符串转换成json对象
     * @param object
     * @return
     */
    public static String createJsonString(Object object){
        String jsonString = "";
        try {
            jsonString = JSON.toJSONString(object);
        }catch (Exception e){

        }
        return jsonString;
    }
}
