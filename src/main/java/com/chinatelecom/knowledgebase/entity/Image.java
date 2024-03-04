package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/3/1 11:40
 * @Description
 * @Version 1.0
 */
@Data
public class Image {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private String imageUrl;
}
