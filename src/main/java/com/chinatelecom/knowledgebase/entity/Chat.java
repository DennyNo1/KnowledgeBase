package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/8/2 9:50
 * @Description
 * @Version 1.0
 */
@Data
public class Chat {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String question;
    private String answer;
    private String reference;
}
