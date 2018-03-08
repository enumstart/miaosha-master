package com.miaosha.service;

import com.miaosha.domain.MiaoShaOrder;
import com.miaosha.domain.OrderInfo;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by enum on 2018/3/8.
 *
 * 秒杀业务类
 */
@Service
public class MiaoShaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private MiaoShaOrderService miaoShaOrderService;

    /**
     * 秒杀
     * @param user
     * @param goodsVo
     * @return
     */
    public OrderInfo doMiaoSha(MiaoShaUser user, GoodsVo goodsVo) {
        //减少秒杀库存
        goodsService.reduceStock(goodsVo);
        //生成订单order_info maiosha_order
        return miaoShaOrderService.createOrder(user, goodsVo);
    }
}
