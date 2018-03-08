package com.miaosha.service;

import com.miaosha.dao.MiaoShaOrderDao;
import com.miaosha.domain.MiaoShaOrder;
import com.miaosha.domain.OrderInfo;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by enum on 2018/3/8.
 *
 * 秒杀订单 业务类
 */
@Service
public class MiaoShaOrderService {

    @Autowired
    private MiaoShaOrderDao miaoShaOrderDao;

    /**
     * 根据userId goodsId 查询秒杀订单
     * @param id
     * @param goodsId
     * @return
     */
    public MiaoShaOrder findOrderByUserIdAndGoodId(Long userId, Long goodsId) {
        return miaoShaOrderDao.findOrderByUserIdAndGoodId(userId, goodsId);
    }

    /**
     * 生成订单
     *
     * 先生成订单 后生成秒杀订单
     * @param user
     * @param goodsVo
     * @return
     */
    public OrderInfo createOrder(MiaoShaUser user, GoodsVo goodsVo) {
        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoShaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = miaoShaOrderDao.inserToOrderInfo(orderInfo);
        //生成秒杀订单
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setGoodsId(goodsVo.getId());
        miaoShaOrder.setUserId(user.getId());
        miaoShaOrder.setOrderId(orderId);
        miaoShaOrderDao.insertToMiaoShaOrder(miaoShaOrder);
        return orderInfo;
    }
}
