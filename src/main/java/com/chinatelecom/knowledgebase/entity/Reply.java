package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Denny
 * @Date 2024/1/23 16:24
 * @Description
 * @Version 1.0
 */
@Data
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer userId;
    private LocalDateTime date;
    private Integer commentId;
    private String secondReplyName;

}
