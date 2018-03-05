package com.miaosha.common.redis.operate;

import com.miaosha.common.redis.base.BasePrefix;

/**
 * Created by pc on 2018/3/5.
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
