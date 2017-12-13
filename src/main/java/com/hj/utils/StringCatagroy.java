package com.hj.utils;

/**
 * Created by hongjin on 2017/12/2.
 */
public class StringCatagroy {
    public static boolean hj_strIsNullOrEmpty(String string){
        if (string.equals("") || string==null){
            return true;
        }
        return false;
    }

    /**
     *
     * @param originStr
     * @return 返回字符串的反转字符串
     */
    public static String hj_reverseStr(String originStr){
        if (originStr == null || originStr.length()<=1) return originStr;
        return hj_reverseStr(originStr.substring(1)+originStr.charAt(0));
    }
}
