package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.Reply;
import com.chinatelecom.knowledgebase.service.impl.ReplyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/1/23 16:31
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    ReplyImpl replyImpl;
    //添加回复
    @PostMapping("/add")
    public R addComment(@RequestBody Reply reply)
    {
        if(reply.getContent()==null)
            return R.error("请输入回复的内容");
        boolean saveRes = replyImpl.save(reply);
        if (saveRes) {
            return R.success(null, "添加回复成功");
        } else {
            throw new RuntimeException("添加评论失败，可能是由于数据库操作异常");
        }
    }
}
