package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Denny
 * @Date 2024/1/17 10:22
 * @Description
 * @Version 1.0
 */

@Data
public class Video
{
    private int id;
    private String type;
    private int uploaderId;
    private LocalDateTime date;

    private String clickCount;
    private String title;
    private String url;
}
