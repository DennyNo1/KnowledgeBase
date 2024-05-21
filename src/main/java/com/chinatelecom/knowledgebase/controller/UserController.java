package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import com.chinatelecom.knowledgebase.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    //验证登录。现在我假设，前端传过来的是用户名和密码。
    public R<Map<String, Object>> login(@RequestBody User user) throws JsonProcessingException {

        String username=user.getUsername();
        String password = user.getPassword();
        //wrapper是一个条件构造器。现在可以理解成where语句。
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        //查询
        User one = userImpl.getOne(wrapper);

        //log.info(one.getPhone());从数据库查询到的对象存在
        if(one==null)
        {


            return R.error("不存在该用户，请联系管理员或重新输入用户名");
        }
        //只有登录成功才需要传送JWT
        if(password.equals(one.getPassword()))
        {

            Map<String, Object> claims=new HashMap<>();
            claims.put("username",username);

            //密码一般不明文传输
//            claims.put("password",password);
            claims.put("role",one.getRole());//身份
            String jwt = JwtUtils.generateJwt(claims);
            //创建一个map，作为返回消息吧
            Map<String, Object> response=new HashMap();
            response.put("userInfo",one);
            response.put("jwt",jwt);

            return R.success(response,"登录成功");
        }
        else return R.error("请输入正确的密码");
        //return one;
    }

    //查询是否存在该用户，并返回它的安全问题
    @GetMapping("/check")
    public R check(@RequestParam(name = "username",required = true) String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        try{
            User one = userImpl.getOne(queryWrapper);
            if(one==null)
                return R.error("该用户名不存在，请重新输入");
            else return R.success(one,"成功查询到该用户");
        }
        catch (Exception e){
            return R.error(e.getMessage());
        }


    }

}
