package com.miaosha.common.redis.base;

/**
 * Created by pc on 2018/3/5.
 */
public interface KeyPrefix {
    /**
     * 得到过期时间
     * @return
     */
    int expireSeconds();

    /**
     * 取得key的前缀
     * @return
     */
    String getPrefix();
}
