package com.chinatelecom.knowledgebase.entity;

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
    private Integer id;
    private int authorId;
    private LocalDateTime date;
    private String content;
    private String imageUrls;
    private String videoUrls;
    private int likeCount;
}
