package com.joy.xxfy.informationallyt.publish.utils;

public class UrlUtil {
    public static String getVersion(String url){
        try{
            String[] split = url.split("/");
            return split[3];
        }catch (Exception e){
            return null;
        }
    }
}
