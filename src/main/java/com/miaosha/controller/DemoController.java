package com.miaosha.controller;

import com.miaosha.common.redis.RedisService;
import com.miaosha.common.redis.operate.UserKey;
import com.miaosha.common.result.CodeMsg;
import com.miaosha.common.result.Result;
import com.miaosha.domain.User;
import com.miaosha.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试用
 * Created by enum on 2018/3/4.
 */
@Controller
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/demo")
    public String home(Model model){
        model.addAttribute("name", "测试");
        return "hello";
    }

    @RequestMapping("/testsuccess")
    @ResponseBody
    public Result<String> testSuccess(){
        return Result.success("测试成功情况");
    }

    @RequestMapping("/testerror")
    @ResponseBody
    public Result<CodeMsg> testError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 测试数据库
     * @return
     */
    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> doGet(){
        User user = userService.getByUserId(1);
        return Result.success(user);
    }

    /**
     * 测试数据库事务
     * @return
     */
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> testTx(){
        Boolean testTx = userService.testTx();
        return Result.success(testTx);
    }

    /**
     * 测试redis
     * @return
     */
    @RequestMapping("/redis/testget")
    @ResponseBody
    public Result<String> testRedisGet(){
        redisService.set(UserKey.getById, "1", "hello");
        String str = redisService.get(UserKey.getById, "1", String.class);
        return Result.success(str);
    }
}
