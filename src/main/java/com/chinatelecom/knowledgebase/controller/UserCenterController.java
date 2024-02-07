package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinatelecom.knowledgebase.DTO.UserCenterDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Question;
import com.chinatelecom.knowledgebase.entity.Reply;
import com.chinatelecom.knowledgebase.service.impl.QuestionImpl;
import com.chinatelecom.knowledgebase.service.impl.ReplyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/2/7 16:19
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserCenterController
{
    @Autowired
    QuestionImpl questionImpl;
    @Autowired
    ReplyImpl replyImpl;
    @GetMapping()
    public R<UserCenterDTO> getUserCenter(@RequestParam(name = "userId",required = true) int userId){
        int questionCount=0;
        int replyCount=0;

         QueryWrapper<Question> questionQueryWrapper=new QueryWrapper<>();
         questionQueryWrapper.eq("questioner_id",userId);
         questionCount=questionImpl.count(questionQueryWrapper);

         QueryWrapper<Reply> replyQueryWrapper=new QueryWrapper<>();
         replyQueryWrapper.eq("author_id",userId);
         replyCount=replyImpl.count(replyQueryWrapper);

         UserCenterDTO userCenterDTO = new UserCenterDTO();
         userCenterDTO.setQuestionCount(questionCount);
         userCenterDTO.setReplyCount(replyCount);

         return R.success(userCenterDTO,"传输个人中心所需数据成功");
     }
}
