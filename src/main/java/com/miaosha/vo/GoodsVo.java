package com.miaosha.vo;

import com.miaosha.domain.Goods;

import java.util.Date;

/**
 * Created by pc on 2018/3/7.
 */
public class GoodsVo extends Goods {
    private Double miaoShaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

    public Double getMiaoShaPrice() {
        return miaoShaPrice;
    }

    public void setMiaoShaPrice(Double miaoShaPrice) {
        this.miaoShaPrice = miaoShaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
