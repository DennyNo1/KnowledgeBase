package com.chinatelecom.knowledgebase.entity;

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
    private int id;
    private int userId;
    private LocalDateTime date;
    private String content;
    private String belongType;
    private int belongId;
}
