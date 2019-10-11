package com.majiang.user.majianguser.utils;


import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.UUID;

public class TokenUtil {

    public static synchronized String getNewToken(){
        return UUID.randomUUID().toString();
    }






}
