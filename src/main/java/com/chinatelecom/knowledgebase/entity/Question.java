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
    //这里指问题是否被回复
    private Integer isSolved;
    private Integer isChecked;
    private String type;
    private int clickCount;
    private String assignTo;
}
