package com.miaosha.common.Util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by enum on 2018/3/5.
 */
public class MD5Util {

    private static final String salt = "3ba9ades34";

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String inputPassToFormPass(String inputPass){
        String  str = "" + salt.charAt(0) + salt.charAt(3) + inputPass + salt.charAt(2) + salt.charAt(6);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt){
        String  str = "" + salt.charAt(0) + salt.charAt(3) + formPass + salt.charAt(2) + salt.charAt(6);
        return md5(str);
    }

    public static void main(String[] args) {
//        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
    }
}
