package com.miaosha.service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.domain.MiaoShaGoods;
import com.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc on 2018/3/7.
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    /**
     * 查询秒杀商品
     * @return
     */
    public List<GoodsVo> getGoodsVO(){
        return goodsDao.getGoodsVo();
    }

    /**
     * 根据id查询秒杀商品
     * @param goodsId
     * @return
     */
    public GoodsVo getGoodsById(Long goodsId) {
        return goodsDao.getGoodsById(goodsId);
    }

    /**
     * 库存减少1
     * @param goodsVo
     */
    public void reduceStock(GoodsVo goodsVo) {
        MiaoShaGoods miaoShaGoods = new MiaoShaGoods();
        miaoShaGoods.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(miaoShaGoods);
    }
}
