package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.ReplyDTO;
import com.chinatelecom.knowledgebase.entity.Reply;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.mapper.ReplyMapper;
import com.chinatelecom.knowledgebase.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/23 16:29
 * @Description
 * @Version 1.0
 */
@Service
public class ReplyImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService
{
    @Autowired
    UserImpl userImpl;
    public List<Reply> getReplyList(long commentId)
    {
        QueryWrapper<Reply> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId);
        List<Reply> list = this.list(queryWrapper);
        return list;
    }

    public List<ReplyDTO> getReplyDTOList(long commentId){

        List<ReplyDTO> res=new ArrayList<>();
        List<Reply> replyList = this.getReplyList(commentId);
        for (Reply reply: replyList
             ) {
            Integer userId = reply.getUserId();
            User oneUser = userImpl.getOneUser(userId);
            ReplyDTO replyDTO=new ReplyDTO();
            replyDTO.setReply(reply);
            replyDTO.setAvatar(oneUser.getAvatar());
            replyDTO.setDepartment(oneUser.getDepartment());
            replyDTO.setNickName(oneUser.getNickName());
            replyDTO.setRole(oneUser.getRole());
            res.add(replyDTO);
        }
        return res;

    }

}
