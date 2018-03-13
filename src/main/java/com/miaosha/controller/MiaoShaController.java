package com.miaosha.controller;

import com.miaosha.common.result.CodeMsg;
import com.miaosha.domain.MiaoShaOrder;
import com.miaosha.domain.OrderInfo;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoShaOrderService;
import com.miaosha.service.MiaoShaService;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

/**
 * Created by enum on 2018/3/8.
 *
 * 秒杀 控制器
 */
@RequestMapping("/miaosha")
@Controller
public class MiaoShaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoShaOrderService miaoShaOrderService;

    @Autowired
    private MiaoShaService miaoShaService;

    @RequestMapping("/do_miaosha")
    public String doMiaoSha(Model model, MiaoShaUser user, @PathParam("goodsId")Long goodsId){
        //未登入
        if (null == user){
            return "login";
        }
        model.addAttribute("user", user);
        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsById(goodsId);
        if (!(goodsVo.getStockCount() > 0)){
            model.addAttribute("error", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否重复秒杀
        MiaoShaOrder miaoShaOrder = miaoShaOrderService.findOrderByUserIdAndGoodId(user.getId(), goodsId);
        if (null != miaoShaOrder){
            model.addAttribute("error", CodeMsg.MIAO_SHA_REPEAT.getMsg());
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoShaService.doMiaoSha(user, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "order_detail";
    }
}
