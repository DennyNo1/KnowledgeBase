package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.Question;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.QuestionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 15:11
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController
{
    @Autowired
    CommentImpl commentImpl;
    @Autowired
    QuestionImpl questionImpl;

    //参考了下b站的评论区，可以把所有评论放在一页里。所以没必要像贴吧那样复杂化分页。
    @GetMapping()
    public R<List<CommentDTO>> getCommentList(
            @RequestParam(name = "belongType",required = true) String belongType,//评论所属的内容
            @RequestParam(name = "belongId",required = true) int belongId,//评论所属内容的id
            @RequestParam(name = "userId",required = false) String userId//因为有可能是空值。传过来的string”“,没法转换成int的某个值。所以直接用string对接string。

    ){
        int loginUserId;
        if(userId.equals(""))
        {
            loginUserId=0;
        }
        else {
            loginUserId=Integer.valueOf(userId);
        }
        List<CommentDTO> commentDTOList = commentImpl.getCommentList(belongType, belongId,loginUserId);
        return R.success(commentDTOList,"成功传输评论区");

    }

    //添加评论
    @PostMapping("/add")
    public R addComment(@RequestBody Comment comment)
    {
        if(comment.getContent()==null)
            return R.error("评论失败，请输入评论的内容");
        boolean saveRes = commentImpl.save(comment);
        if (saveRes) {
            //如果是对问题进行评论，再多加一个步骤,让该问题的记录表明被回复
            if(comment.getBelongType().equals("question"))
            {
                //这一个步骤，只是更新一个值，只有数据库错误的可能
                Question byId = questionImpl.getById(comment.getBelongId());
                byId.setIsSolved(1);
                questionImpl.updateById(byId);
            }
            return R.success(null, "添加评论成功");
        } else {
            throw new RuntimeException("添加评论失败，可能是由于数据库操作异常");
        }
    }



}
