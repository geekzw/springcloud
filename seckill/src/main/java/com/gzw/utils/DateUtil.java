package com.gzw.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gujian on 2017/10/11.
 */
public class DateUtil {

    public static String getFormatDate(Date d){

        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a1=dateformat1.format(new Date());
        return a1;
    }
}
