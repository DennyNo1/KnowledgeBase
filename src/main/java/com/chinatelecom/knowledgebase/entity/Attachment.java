package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/3/1 11:41
 * @Description
 * @Version 1.0
 */
@Data
public class Attachment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private String attachmentUrl;
}