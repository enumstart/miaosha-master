package com.miaosha.dao;

import com.miaosha.domain.MiaoShaGoods;
import com.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by pc on 2018/3/7.
 */
@Mapper
public interface GoodsDao {

    @Select("select gs.*,mgs.miaosha_price, mgs.stock_count, mgs.start_date, mgs.end_date from goods gs left join miaosha_goods mgs on gs.id = mgs.goods_id")
    List<GoodsVo> getGoodsVo();

    /**
     * 根据id查询秒杀商品
     * @param goodsId
     * @return
     */
    @Select("select gs.*,mgs.miaosha_price, mgs.stock_count, mgs.start_date, mgs.end_date from goods gs left join miaosha_goods mgs on gs.id = mgs.goods_id where gs.id = #{goodsId}")
    GoodsVo getGoodsById(@Param("goodsId") Long goodsId);

    /**
     * 库存减1
     * @param miaoShaGoods
     */
    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{goodsId}")
    void reduceStock(MiaoShaGoods miaoShaGoods);
}
