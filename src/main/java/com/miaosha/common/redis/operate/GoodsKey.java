package com.miaosha.common.redis.operate;

import com.miaosha.common.redis.base.BasePrefix;

/**
 * Created by enum on 2018/3/13.
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSeconds, String prefix) {
        super(prefix);
    }

    public static GoodsKey goodsKey = new GoodsKey(60,"gl");//默认过期时间是60秒
}
