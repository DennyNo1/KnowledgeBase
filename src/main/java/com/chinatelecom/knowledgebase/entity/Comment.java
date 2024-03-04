package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Denny
 * @Date 2024/1/17 10:52
 * @Description
 * @Version 1.0
 */

@Data
public class Comment
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private LocalDateTime date;
    private String content;
    private String belongType;
    private int belongId;
    private int likeCount;
}
