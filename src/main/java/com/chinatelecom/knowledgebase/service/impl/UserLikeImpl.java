package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.mapper.UserLikeMapper;
import com.chinatelecom.knowledgebase.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Denny
 * @Date 2024/1/26 15:02
 * @Description
 * @Version 1.0
 */
@Service
public class UserLikeImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {
    @Autowired
    ArticleImpl articleImpl;

    public R updateLike(@RequestBody UserLike userLike){
        QueryWrapper<UserLike> queryWrapper=new QueryWrapper<>();
        int userId = userLike.getUserId();
        String belongType = userLike.getBelongType();
        int belongId = userLike.getBelongId();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("belong_type",belongType);
        queryWrapper.eq("belong_id",belongId);
        UserLike one = this.getOne(queryWrapper);
        if(one!=null)
            return R.error("该用户已点赞，无法重复点赞");
        this.save(userLike);
        //目前只做article的点赞功能
        if(belongType.equals("article")){
            //找到这条记录

            try {
                Article byId = articleImpl.getById(belongId);
                Integer likeCount = byId.getLikeCount();
                byId.setLikeCount(likeCount+1);
                articleImpl.updateById(byId);
            } catch (Exception e) {
                return R.error("该内容不存在");
            }
        }
        return R.success(null,"点赞成功");

    }
    public boolean isLikeOrNot(String belongType,int belongId,int userId){
        QueryWrapper<UserLike> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("belong_type",belongType);
        queryWrapper.eq("belong_id",belongId);
        queryWrapper.eq("user_id",userId);
        //可能存在对同一文章单一用户的多条点赞记录这种错误情形
        int count = this.count(queryWrapper);

        if(count==0)
            return false;
        else return true;

    }
}
