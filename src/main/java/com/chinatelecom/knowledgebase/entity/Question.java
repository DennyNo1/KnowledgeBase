package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Denny
 * @Date 2024/1/22 16:07
 * @Description
 * @Version 1.0
 */
@Data
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int questionerId;
    private LocalDateTime date;
    private String title;
    private String content;
    private String imageUrls;
    private String videoUrls;
    private String isSolved;
}
