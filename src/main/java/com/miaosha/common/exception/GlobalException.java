package com.miaosha.common.exception;

import com.miaosha.common.result.CodeMsg;

/**
 * Created by enum on 2018/3/6.
 */
public class GlobalException extends RuntimeException {
    private CodeMsg msg;

    public GlobalException(CodeMsg msg){
        super(msg.toString());
        this.msg = msg;
    }

    public CodeMsg getMsg() {
        return msg;
    }
}
