package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Reply;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/26 15:49
 * @Description
 * @Version 1.0
 */
@Data
public class ReplyDTO
{
    private Reply reply;
    private String username;
    private String avatar;
    private boolean isLike=false;
}
