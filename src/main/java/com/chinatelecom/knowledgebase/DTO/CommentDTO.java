package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Comment;
import lombok.Data;

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
    private String nickName;
    private String department;
    private String avatar;
    //是否被已登录的用户点赞
    private boolean isLiked=false;
}
