package com.majiang.user.majianguser.utils;



import java.util.UUID;

public class TokenUtil {

    public static synchronized String getNewToken(){
        return UUID.randomUUID().toString();
    }



}
