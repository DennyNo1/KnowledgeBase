package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.DingdingMsgApi;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.VerifyObject;
import com.chinatelecom.knowledgebase.service.impl.DingdingMsgApiImpl;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import com.chinatelecom.knowledgebase.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        else return R.error("您输入的密码有误，请重新输入");
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
            //应对特殊情况，没有设定过初始密码但选择忘记密码

            else if(one.getSafeQuestion()==null){
                return R.error("请先登录。如果遗忘默认密码，请联系慧问工作室徐慧15305809539。");
            }
            return R.success(one,"成功传输用户信息");

        }
        catch (Exception e){
            return R.error(e.getMessage());
        }


    }
    @Autowired
     DingdingMsgApiImpl dingdingMsgApiImpl;
    //新版重置密码
    @PostMapping("/resetPwd")
    public R resetPwd(@RequestBody User user)//只要有手机号即可
    {
        System.out.println("resetPwd");
        try{
            dingdingMsgApiImpl.insertAndProcess(user.getPhone());
            return R.success(null,"获取验证码成功");
        }
        catch (Exception e)
        {
            return R.error(e.getMessage());
        }
    }

    //对验证码的验证
    @PostMapping("/verifyCode")
    public boolean verifyCode (@RequestBody VerifyObject verifyObject){
        System.out.println(verifyObject.getCode());
        QueryWrapper<DingdingMsgApi> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("TO_NUMBER",verifyObject.getPhone());
        queryWrapper.like("MSG","触点");
        queryWrapper.like("MSG",verifyObject.getCode());
        List<DingdingMsgApi> list = dingdingMsgApiImpl.list(queryWrapper);
        if(list.size()!=1) return false;
        //验证时间是否未半小时内
        String inputTime = list.get(0).getInputTime();
        System.out.println(inputTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 解析时间字符串为LocalDateTime
        LocalDateTime targetTime = LocalDateTime.parse(inputTime, formatter);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算两个时间点的差异
        Duration duration = Duration.between(now, targetTime);

        // 判断差异是否在30分钟内
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(duration.toMillis());
        if (Math.abs(diffMinutes) <= 30) {
            return true;
        }
        return false;

    }


    //重置密码
    @PostMapping("/reset")
    public R reset(@RequestBody User user){
        //先通过用户名查出用户
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        //因为有了上一步check，正常来说是能查询到这个用户
        User one = userImpl.getOne(queryWrapper);
       
        //这个是用于初始化密码
        if(one.getSafeQuestion()==null||one.getSafeQuestion().equals(""))
        {
            one.setPassword(user.getPassword());
            one.setSafeQuestion(user.getSafeQuestion());
            one.setSafeAnswer(user.getSafeAnswer());
            try{
                //修改的时候可能报错
                userImpl.updateById(one);
                return R.success(null,"初始化密码成功");

            }
            catch (Exception e){
                return R.error(e.getMessage());
            }
        }

        else{
            //这个是用于重置密码
            if(one.getSafeAnswer().equals(user.getSafeAnswer())&&one.getSafeQuestion().equals(user.getSafeQuestion()))


            {
                one.setPassword(user.getPassword());
                try{
                    //修改的时候可能报错
                    userImpl.updateById(one);
                    return R.success(null,"重置密码成功");

                }
                catch (Exception e){
                    return R.error(e.getMessage());
                }
            }
            else return R.error("安全问题的答案不符合，请重新输入");
        }


    }

}
