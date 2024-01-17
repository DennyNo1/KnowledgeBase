package com.chinatelecom.knowledgebase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/1/16 9:03
 * @Description
 * @Version 1.0
 */
@RequestMapping("/test")
@RestController
public class test {

    @GetMapping
    public void testRequest(){
        System.out.println("receive request successfully");
    }
}
