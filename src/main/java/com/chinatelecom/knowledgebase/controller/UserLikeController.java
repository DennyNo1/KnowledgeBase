package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.service.impl.UserLikeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/3/5 16:00
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/like")
public class UserLikeController {
    @Autowired
    UserLikeImpl userLikeImpl;
    @PostMapping
    public R updateLike(@RequestBody UserLike userLike)
    {
        return userLikeImpl.updateLike(userLike);

    }
}
