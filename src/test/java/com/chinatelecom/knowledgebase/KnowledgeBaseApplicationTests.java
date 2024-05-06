package com.chinatelecom.knowledgebase;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class KnowledgeBaseApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private UserMapper userMapper;



    @Test
    public void genJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","Tom");

        String jwt = Jwts.builder()
                .setClaims(claims) //自定义内容(载荷)
                .signWith(SignatureAlgorithm.HS256,"ChinaTelecom") //签名算法
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3600)) //有效期，设置成一小时
                .compact();

        System.out.println(jwt);
    }
    @Test
    public void parseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("ChinaTelecom")//指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzE0OTc3MjU1LCJ1c2VybmFtZSI6IlRvbSJ9.Qi3eY__9x5IvzSep3G8eG0xPZHifMKRJA40Z_p20aDA")
                .getBody();

        System.out.println(claims);
    }

}
