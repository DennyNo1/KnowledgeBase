package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/17 9:59
 * @Description 用户实体类
 * @Version 1.0
 */

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private String phone;
    private String role;
    private String location;
    private String department;
    private String avatar;
    private String saleCode;
    private String sale;
    private String safeQuestion;
    private String safeAnswer;
}
