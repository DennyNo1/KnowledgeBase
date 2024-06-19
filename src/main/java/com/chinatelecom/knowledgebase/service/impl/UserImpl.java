package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.mapper.UserMapper;
import com.chinatelecom.knowledgebase.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Denny
 * @Date 2024/1/17 10:10
 * @Description
 * @Version 1.0
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService
{
    public User getOneUser(int id)
    {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        User one = this.getOne(queryWrapper);
        return one;
    }
}
