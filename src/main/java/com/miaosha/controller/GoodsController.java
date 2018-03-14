package com.miaosha.controller;

import com.miaosha.common.redis.RedisService;
import com.miaosha.common.redis.operate.GoodsKey;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoShaUserService;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.MiaoShaUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

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
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String goodList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user){
        List<GoodsVo> goodsVoList = goodsService.getGoodsVO();
        //取缓存
        String html = redisService.get(GoodsKey.goodsKey, "gl", String.class);
        if (StringUtils.isNotBlank(html)){
            return html;
        }

        model.addAttribute("goodsList", goodsVoList);
        model.addAttribute("user", user);

        //手动渲染
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        String createHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (StringUtils.isNoneBlank(createHtml)){
            redisService.set(GoodsKey.goodsKey, "gl", createHtml);
        }

        return createHtml;
    }

    /**
     * 根据goodsId查找秒杀商品
     * @param goodsId
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String goodsDetail(HttpServletResponse response, HttpServletRequest request, @PathVariable Long goodsId, MiaoShaUser user, Model model){
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.goodsKey, "gl"+goodsId, String.class);
        if (StringUtils.isNoneBlank(html)){
            return html;
        }

        GoodsVo goods = goodsService.getGoodsById(goodsId);
        Long startTime = goods.getStartDate().getTime();
        Long endTime = goods.getEndDate().getTime();
        Long currentTime = System.currentTimeMillis();
        int status = 0;
        int remainTime = 0;

        if (startTime > currentTime){//未开始
            status = 0;
            remainTime = (int) ((startTime - currentTime)/1000);
        }else if(endTime < currentTime){//已结束
            status = 2;
        }else{//进行中
            status = 1;
        }
        model.addAttribute("goods", goods);
        model.addAttribute("status", status);
        model.addAttribute("remainTime", remainTime);

        //手动生成
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        String createHtml = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (StringUtils.isNoneBlank(createHtml)){
            redisService.set(GoodsKey.goodsKey, "gl"+goodsId, createHtml);
        }
        return createHtml;
    }
}
