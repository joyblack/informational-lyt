package com.joy.xxfy.informationallyt.publish.utils;

public class SqlUtil {
    /**
     * 包装为模糊查询字符串
     */
    public static String getLikeString(String parameter){
        return parameter == null? "%%" : "%" +  parameter + "%";
    }
}
