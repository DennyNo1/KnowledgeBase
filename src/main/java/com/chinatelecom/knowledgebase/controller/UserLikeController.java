package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.UserLikeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/3/5 16:00
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/like")
public class UserLikeController {
    @Autowired
    UserLikeImpl userLikeImpl;
    @Autowired
    CommentImpl commentImpl;
    @PostMapping
    public R updateLike(@RequestBody UserLike userLike)
    {

        if(userLike.getBelongType()==null||userLike.getUserId()==0||userLike.getBelongId()==0)
            return R.error("点赞失败，前端传输缺少所需数据");
        //先只做评论的点赞.先给comment表的记录的likeCount+1.
        if(userLike.getBelongType().equals("comment")) {
            commentImpl.likeComment(userLike.getBelongId());
        }
        //因为每个用户只限制点赞一次，所以是save
        boolean save = userLikeImpl.save(userLike);
        if(!save) return R.error("该点赞记录已存在，或数据库异常");
        else return R.success(null,"点赞成功");


    }
}
