package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/17 9:59
 * @Description 用户实体类
 * @Version 1.0
 */
@TableName("user")
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String avatar;
    private String role;
}
