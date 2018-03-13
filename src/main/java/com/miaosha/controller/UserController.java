package com.miaosha.controller;

import com.miaosha.common.result.Result;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by enum on 2018/3/12.
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoShaUser> info(Model model, MiaoShaUser user){
        return Result.success(user);
    }
}
