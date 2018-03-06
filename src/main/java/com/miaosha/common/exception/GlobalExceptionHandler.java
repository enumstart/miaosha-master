package com.miaosha.common.exception;

import com.miaosha.common.result.CodeMsg;
import com.miaosha.common.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by enum on 2018/3/6.
 */
@ControllerAdvice//advice切面
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)//接收所有异常
    public Result<String> exceptionHadler(Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException ge = (GlobalException) e;
            return Result.error(ge.getMsg());
        }else if (e instanceof BindException){
            BindException be = (BindException) e;
            List<ObjectError> errorList = be.getAllErrors();
            ObjectError oe = errorList.get(0);
            String msg = oe.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
