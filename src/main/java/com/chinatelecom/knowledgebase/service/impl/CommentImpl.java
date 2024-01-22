package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.mapper.CommentMapper;
import com.chinatelecom.knowledgebase.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Denny
 * @Date 2024/1/17 11:01
 * @Description
 * @Version 1.0
 */
@Service
public class CommentImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserImpl userImpl;
    public List<CommentDTO> getComments(int belong_id, String belong_type)
    {
        ArrayList<List> comments = new ArrayList<>();
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("belong_id",belong_id);
        queryWrapper.eq("belong_type",belong_type);
        List<Comment> list = this.list(queryWrapper);
        List<CommentDTO> res=new ArrayList<>();
        for (Comment comment :list) {
            int userId = comment.getUserId();

            User oneUser = userImpl.getOneUser(userId);
            String nickName = oneUser.getNickName();
            String department = oneUser.getDepartment();
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment(comment);
            commentDTO.setDepartment(department);
            commentDTO.setNickName(nickName);
            res.add(commentDTO);
        }
        return res;
    }
}
