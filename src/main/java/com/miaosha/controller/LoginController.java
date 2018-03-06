package com.miaosha.controller;

import com.miaosha.common.result.CodeMsg;
import com.miaosha.common.result.Result;
import com.miaosha.service.MiaoShaUserService;
import com.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by enum on 2018/3/5.
 */
@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
//        if (StringUtils.isBlank(loginVo.getMobile())) return Result.error(CodeMsg.MOBILE_EMPTY);
//        if (StringUtils.isBlank(loginVo.getPassword())) return Result.error(CodeMsg.PASSWORD_EMPTY);
        miaoShaUserService.login(response, loginVo);
        return Result.success(true);
    }
}
