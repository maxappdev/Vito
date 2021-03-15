package com.vito.webapp.backend.utils;

public class VitoUtils {

    public static boolean ok(String str){
        return (str != null) && (!str.isEmpty()) && (!str.isBlank());
    }
}
