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
}
