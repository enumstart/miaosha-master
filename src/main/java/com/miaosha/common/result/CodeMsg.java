package com.miaosha.common.result;

/**
 * Created by enum on 2018/3/4.
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "绑定异常：%s");
    //登入模块5002
    public static CodeMsg SESSION_ERROR = new CodeMsg(5002001, "session异常");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(5002002, "登入密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(5002003, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(5002004, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(5002005, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(5002006, "密码错误");
    //商品模块5003
    //订单模块5004
    //秒杀模块5005
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(5005001, "秒杀已结束");
    public static CodeMsg MIAO_SHA_REPEAT = new CodeMsg(5005002, "重复参与秒杀活动");

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String msg = String.format(this.msg, args);
        return new CodeMsg(code, msg);
    }

    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
