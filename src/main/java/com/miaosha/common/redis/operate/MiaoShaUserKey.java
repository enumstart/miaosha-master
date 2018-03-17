package com.miaosha.common.redis.operate;

import com.miaosha.common.redis.base.BasePrefix;

/**
 * Created by enum on 2018/3/6.
 */
public class MiaoShaUserKey extends BasePrefix {

    private static final int EXPERIE_TIME = 3600*60*2;

    public MiaoShaUserKey(String prefix) {
        super(EXPERIE_TIME, prefix);
    }
    public static MiaoShaUserKey USER_TOKEN = new MiaoShaUserKey("user_token");
    public static MiaoShaUserKey USER_ID = new MiaoShaUserKey("id");
}
