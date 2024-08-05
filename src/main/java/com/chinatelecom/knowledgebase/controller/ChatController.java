package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.Chat;
import com.chinatelecom.knowledgebase.service.impl.ChatImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/8/2 9:57
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatImpl chatImpl;
    @PostMapping("/collect")
        public R collectChatRecord(@RequestBody Chat chat){
        boolean saveRes = chatImpl.save(chat);
        if(saveRes)
            return R.success(null,"成功收集该问题的回复");
        else return R.error("收集失败");
        }
}
