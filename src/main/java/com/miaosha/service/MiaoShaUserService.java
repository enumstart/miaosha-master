package com.miaosha.service;

import com.miaosha.common.Util.MD5Util;
import com.miaosha.common.Util.UUIDUtil;
import com.miaosha.common.exception.GlobalException;
import com.miaosha.common.redis.RedisService;
import com.miaosha.common.redis.operate.MiaoShaUserKey;
import com.miaosha.common.result.CodeMsg;
import com.miaosha.dao.MiaoShaUserDao;
import com.miaosha.vo.LoginVo;
import com.miaosha.vo.MiaoShaUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by enum on 2018/3/5.
 */
@Service
public class MiaoShaUserService {

    public static final String MIAO_SHA_USER_TOKEN = "miao_sha_user_token";

    @Autowired
    private MiaoShaUserDao miaoShaUserDao;

    @Autowired
    private RedisService redisService;

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public MiaoShaUser getById(long id){
        MiaoShaUser user = redisService.get(MiaoShaUserKey.USER_ID, "id" + id, MiaoShaUser.class);
        if (StringUtils.isNotBlank(user)){
            return user;
        }
        user = miaoShaUserDao.getById(id);
        if ( null != user){
            redisService.set(MiaoShaUserKey.USER_ID, "id" + id, user);
        }
        return user;
    }

    /**
     * 登入
     * @param response
     * @param loginVo
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (null == loginVo){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoShaUser user = getById(Long.parseLong(mobile));
        if (null == user) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String dbSale = user.getSalt();
        String machPass = MD5Util.formPassToDBPass(formPass, dbSale);
        if (! dbPass.equals(machPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        addCookie(response, UUIDUtil.generUUID(), user);
        return true;
    }

    /**
     * 添加cookie到客户端
     * @param response
     * @param user
     */
    private void addCookie(HttpServletResponse response, String uuid, MiaoShaUser user) {
        //放入redis
        redisService.set(MiaoShaUserKey.USER_TOKEN, uuid, user);
        //放入cookie
        Cookie cookie = new Cookie(MIAO_SHA_USER_TOKEN, uuid);
        cookie.setMaxAge(MiaoShaUserKey.USER_TOKEN.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 根据token取得user 同时延长session过期时间
     * @param response
     * @param token
     * @return
     */
    public MiaoShaUser getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isNotBlank(token)){
            MiaoShaUser user = redisService.get(MiaoShaUserKey.USER_TOKEN, token, MiaoShaUser.class);
            if (null != user){
                addCookie(response, token, user);//延长session过期时间
            }
            return user;
        }
        return null;
    }
}
