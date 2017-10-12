package com.gzw.utils;


/**
 * Created by wangshengru on 16/1/15.
 * 字符串处理工具
 */
public class StringUtil {

    public static String checkNotNull(String str, String nullStr) {
        return isEmpty(str) ? nullStr : str;
    }

    public static boolean isEmpty(String str) {
        return ((str == null) || str.trim().equals(""));
    }

    public static boolean isNotEmpty(String str) {
        return (str != null && !str.trim().equals(""));
    }

}
