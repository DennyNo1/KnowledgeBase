package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.Video;
import lombok.Data;


/**
 * @Author Denny
 * @Date 2024/1/18 16:24
 * @Description video返回的响应消息，不仅需要video的字段，还需要它上传者、上传时间、部门的字段
 * @Version 1.0
 */
@Data
public class VideoDTO
{
    private Video video;
    private User user;

}
