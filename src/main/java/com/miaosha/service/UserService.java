package com.miaosha.service;

import com.miaosha.dao.UserDao;
import com.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by enum on 2018/3/4.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 测试数据库连接
     * @param id
     * @return
     */
    public User getByUserId(int id){
        return userDao.getByUserId(id);
    }

    /**
     * 测试事务
     * @return
     */
    @Transactional
    public Boolean testTx() {
        User user1 = new User();
        user1.setId(2);
        user1.setName("测试id为2");
        userDao.insert(user1);
        User user2 = new User();
        user2.setId(1);
        user2.setName("测试id为1");
        userDao.insert(user2);
        return true;
    }
}
