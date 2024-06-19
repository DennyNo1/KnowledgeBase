package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/18 22:11
 * @Description
 * @Version 1.0
 */
@Data
public class CommentDTO
{
    private Comment comment;
    //创建该评论的用户的信息
    private User user;
    //是否被已登录的用户点赞
    private boolean isLiked;
    //comment下的reply
    private List<ReplyDTO> replyDTOList;

}
