package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public R<Page<CommentDTO>> getCommentList(
            @RequestParam(name = "belongType",required = true) String belongType,//评论所属的内容
            @RequestParam(name = "belongId",required = true) int belongId,//评论所属内容的id
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize",  defaultValue = "6") int pageSize
    ){
        Page<CommentDTO> commentDTOList = commentImpl.getCommentList(belongType, belongId, page, pageSize);
        return R.success(commentDTOList,"成功传输评论");

    }

    //添加评论
    @PostMapping("/add")
    public R addComment(@RequestBody Comment comment)
    {
        if(comment.getContent()==null)
            return R.error("请输入评论的内容");
        boolean saveRes = commentImpl.save(comment);
        if (saveRes) {
            return R.success(null, "添加评论成功");
        } else {
            throw new RuntimeException("添加评论失败，可能是由于数据库操作异常");
        }
    }


}
