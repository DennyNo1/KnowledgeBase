package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.service.impl.ArticleImpl;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.UserLikeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    ArticleImpl articleImpl;
    @PostMapping
    public R updateLike(@RequestBody UserLike userLike)
    {

        if(userLike.getBelongType()==null||userLike.getUserId()==0||userLike.getBelongId()==0)
            return R.error("点赞失败，前端传输缺少所需数据");
        //先只做评论的点赞.先给comment表的记录的likeCount+1.
        if(userLike.getBelongType().equals("comment")) {
            commentImpl.likeComment(userLike.getBelongId());
        }
        if(userLike.getBelongType().equals("article"))
        {
            articleImpl.likeArticle(userLike.getBelongId());
        }
        //因为每个用户只限制点赞一次，所以是save
        boolean save = userLikeImpl.save(userLike);
        if(!save) return R.error("该点赞记录已存在，或数据库异常");
        else return R.success(null,"点赞成功");

    }
    //给某文章是否被某用户点赞单独做一个接口，以减少浏览量
    @Autowired
    UserLikeImpl getUserLikeImpl;
    @GetMapping("/article")
    public boolean getArticleLike(  @RequestParam(name = "articleId",required = true) int articleId,
                                    @RequestParam(name = "userId",required = true) String userId){
        //假设该用户没登录等情况，传过来userId=""
        Integer user;
        if (userId == null || userId.equals("")) {
            // 用户ID为空或等于空字符串的处理逻辑
             user=0;
        }
        else  user=Integer.valueOf(userId);

        return  userLikeImpl.isLikeOrNot("article",articleId,user);
    }
}
