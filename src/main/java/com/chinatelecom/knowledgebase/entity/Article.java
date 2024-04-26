package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Denny
 * @Date 2024/1/17 10:50
 * @Description
 * @Version 1.0
 */

@Data
public class Article {
    //int和integer，优先考虑integer，因为可以存在null

    @TableId(type = IdType.AUTO)//让id跟随数据库 ID 自增
    private Integer id;
    private String   type;
    private int uploaderId;
    private LocalDateTime date;
    private String content;
    private int clickCount;
    private String title;
    private int commentCount;
    private String label;
    private Integer likeCount;
    private String tag;
    private int top;

}
