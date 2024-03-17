package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Denny
 * @Date 2024/1/17 15:04
 * @Description
 * @Version 1.0
 */
@Slf4j//用于在控制台输出日志
@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserImpl userImpl;

    //简单的逻辑还是写在controller里吧
    @PostMapping("/login")
    //@ResponseBody
    //验证登录。现在我假设，前端会传过来一个用户名和密码。
    public R<User> login(@RequestBody User user) throws JsonProcessingException {

        String username=user.getUsername();
        //wrapper是一个条件构造器。现在可以理解成where语句。
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        //查询
        User one = userImpl.getOne(wrapper);

        //log.info(one.getPhone());从数据库查询到的对象存在
        if(one==null)
        {
            return R.error("不存在该用户");
        }
        if(user.getPassword().equals(one.getPassword()))
        {
            return R.success(one,"登录成功");
        }
        else return R.error("请输入正确的密码");
        //return one;
    }
    @PostMapping("/register")
    public R regiter(@RequestBody User user){

        boolean saveRes = userImpl.save(user);
        if (saveRes) {
            return R.success(null, "新用户注册成功");
        } else {
            throw new RuntimeException("新用户注册失败，可能是由于数据库操作异常");
        }
    }

}
