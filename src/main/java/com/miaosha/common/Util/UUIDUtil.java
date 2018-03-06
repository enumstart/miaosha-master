package com.miaosha.common.Util;

import java.util.UUID;

/**
 * Created by enum on 2018/3/6.
 */
public class UUIDUtil {
    public static String generUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.generUUID());
    }
}
