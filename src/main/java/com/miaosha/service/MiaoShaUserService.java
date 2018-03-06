package com.miaosha.service;

import com.miaosha.common.Util.MD5Util;
import com.miaosha.common.result.CodeMsg;
import com.miaosha.dao.MiaoShaUserDao;
import com.miaosha.vo.LoginVo;
import com.miaosha.vo.MiaoShaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by enum on 2018/3/5.
 */
@Service
public class MiaoShaUserService {

    @Autowired
    private MiaoShaUserDao miaoShaUserDao;

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public MiaoShaUser getById(long id){
        return miaoShaUserDao.getById(id);
    }

    /**
     * 登入
     * @param response
     * @param loginVo
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (null == loginVo){
            throw new RuntimeException();
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoShaUser user = getById(Long.parseLong(mobile));
        if (null == user) {
            throw new RuntimeException();
        }
        String dbPass = user.getPassword();
        String dbSale = user.getSalt();
        String machPass = MD5Util.formPassToDBPass(formPass, dbSale);
        if (! dbPass.equals(machPass)){
            throw new RuntimeException();
        }
        //TODO生成cookie
        return true;
    }
}
