package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.mapper.UserLikeMapper;
import com.chinatelecom.knowledgebase.service.UserLikeService;
import org.springframework.stereotype.Service;

/**
 * @Author Denny
 * @Date 2024/1/26 15:02
 * @Description
 * @Version 1.0
 */
@Service
public class UserLikeImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {
}
