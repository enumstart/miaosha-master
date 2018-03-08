package com.miaosha.dao;

import com.miaosha.domain.MiaoShaOrder;
import com.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * Created by enum on 2018/3/8.
 *
 * 秒杀订单
 */
@Mapper
public interface MiaoShaOrderDao {


    /**
     * 根据userId goodsId查找实体
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    MiaoShaOrder findOrderByUserIdAndGoodId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    /**
     * 插入订单表
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyProperty = "id", keyColumn = "id", before = false, resultType = long.class, statement = "select last_insert_id()")
    long inserToOrderInfo(OrderInfo orderInfo);

    /**
     * 插入秒杀订单表
     * @param miaoShaOrder
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    void insertToMiaoShaOrder(MiaoShaOrder miaoShaOrder);
}
