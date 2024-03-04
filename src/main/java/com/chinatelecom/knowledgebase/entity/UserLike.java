package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/26 11:36
 * @Description
 * @Version 1.0
 */
//这张表就是记录什么用户给什么内容（评论/回答）点了赞，因此没点赞的不需要记录
@Data
public class UserLike {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String type;
    private int typeId;

}
