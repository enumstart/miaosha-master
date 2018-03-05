package com.miaosha.common.redis.operate;

import com.miaosha.common.redis.base.BasePrefix;

/**
 * Created by pc on 2018/3/5.
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
