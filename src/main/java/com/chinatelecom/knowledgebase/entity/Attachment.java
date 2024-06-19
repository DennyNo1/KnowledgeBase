package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

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
    private String url;
    private String name;// 这是附件的名字
    private BigInteger uid;//前端组件生成的附件的uid，也是唯一标识。

}
