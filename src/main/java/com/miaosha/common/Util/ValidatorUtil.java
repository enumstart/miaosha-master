package com.miaosha.common.Util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by enum on 2018/3/5.
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String str){
        if (StringUtils.isBlank(str)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(ValidatorUtil.isMobile("13023992508"));
        System.out.println(ValidatorUtil.isMobile("1302399256"));
    }
}
