package com.miaosha.controller;

import com.miaosha.common.redis.RedisService;
import com.miaosha.service.MiaoShaUserService;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by enum on 2018/3/6.
 */
@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Autowired
    private MiaoShaUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 到商品列表页面  携带了cookie作为session的标志
     * 缺点：这种方式需要每个方法都添加cookie的验证
     * 解决办法：在每个controller方法中注入MiaShaUser对象 这样就可以直接取得
     * @return
     *//*
    @RequestMapping("/to_list")
    public String goodList(HttpServletResponse response, Model model, @CookieValue(value = MiaoShaUserService.MIAO_SHA_USER_TOKEN, required = false) String cookieValue,
                           @RequestParam(value = MiaoShaUserService.MIAO_SHA_USER_TOKEN, required = false) String paramToken){
        if (StringUtils.isBlank(cookieValue) && StringUtils.isBlank(paramToken)){
            return "login";
        }
        String token = cookieValue != null ? cookieValue : paramToken;
        MiaoShaUser user = userService.getUserByToken(response, token);
        if (null == user) return "login";
        model.addAttribute("user", user);
        return "goods_list";
    }*/

    @RequestMapping("/to_list")
    public String goodList(Model model, MiaoShaUser user){
        model.addAttribute("user", user);
        return "goods_list";
    }
}
