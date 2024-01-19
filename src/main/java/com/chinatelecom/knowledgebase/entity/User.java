package com.chinatelecom.knowledgebase.entity;

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
    private int id;
    private String username;
    private String password;
    private String nickName;
    private String phone;
    private String role;
    private String location;
    private String department;
}
