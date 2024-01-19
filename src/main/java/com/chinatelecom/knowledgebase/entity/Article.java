package com.chinatelecom.knowledgebase.entity;

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
    private int id;
    private String type;
    private int uploaderId;
    private LocalDateTime date;
    private String content;
    private String clickCount;
    private String title;

}
