package com.miaosha.controller;

import com.miaosha.common.redis.RedisService;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoShaUserService;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @Autowired
    private GoodsService goodsService;

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

    /**
     * 商品列表
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String goodList(Model model, MiaoShaUser user){
        List<GoodsVo> goodsVoList = goodsService.getGoodsVO();
        model.addAttribute("goodsList", goodsVoList);
        model.addAttribute("user", user);
        return "goods_list";
    }

    /**
     * 根据goodsId查找秒杀商品
     * @param goodsId
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String goodsDetail(@PathVariable Long goodsId, MiaoShaUser user, Model model){
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsById(goodsId);
        Long startTime = goods.getStartDate().getTime();
        Long endTime = goods.getEndDate().getTime();
        Long currentTime = System.currentTimeMillis();
        int status = 0;
        int remainTime = 0;
        if (currentTime > startTime){//未开始
            status = 0;
            remainTime = (int) ((currentTime - startTime)/1000);
        }else if(endTime > currentTime){//已结束
            status = 2;
        }else{//进行中
            status = 1;
        }
        return "goods_detail";
    }
}
