package com.chinatelecom.knowledgebase;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinatelecom.knowledgebase.entity.DingdingMsgApi;
import com.chinatelecom.knowledgebase.service.impl.DingdingMsgApiImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class KnowledgeBaseApplicationTests {
    @Autowired
    public DingdingMsgApiImpl DINGDINGMSGAPIImpl;

    @DS("oracle")
    @Test
    public void testDataSource() {
//        QueryWrapper<DingdingMsgApi> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("SID",11);
//        System.out.println(DINGDINGMSGAPIImpl.list(queryWrapper));
        DINGDINGMSGAPIImpl.insertAndProcess("15305807322");


    }
    @Test
    public void testNumber(){
        // 初始化一个SecureRandom对象用于生成随机数
        SecureRandom secureRandom = new SecureRandom();
        // 创建一个HashSet来存储已经生成的六位数，确保不重复
        Set<Integer> generatedNumbers = new HashSet<>();

        // 目标是生成一个不重复的六位数，直接生成并检查是否已存在
        while (generatedNumbers.size() < 1) { // 循环直到我们得到一个唯一的数
            int number = secureRandom.nextInt(900000) + 100000; // 生成100000到999999之间的随机数
            generatedNumbers.add(number); // 尝试添加到集合中，如果重复则会在下一次循环中重新生成
        }

        // 输出生成的不重复六位数
        for (int uniqueNumber : generatedNumbers) {
            System.out.println("Generated unique 6-digit number: " + uniqueNumber);
        }
    }

}
