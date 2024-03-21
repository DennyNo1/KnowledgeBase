package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.DTO.ReplyDTO;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.mapper.CommentMapper;
import com.chinatelecom.knowledgebase.service.CommentService;
import org.springframework.beans.BeanUtils;
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

    //获取一个视频或者一篇文章或一个需求的所有评论。这三者统称为内容。
    @Autowired
    UserImpl userImpl;
    @Autowired
    UserLikeImpl userLikeImpl;
    @Autowired
    ReplyImpl replyImpl;
/*    public List<CommentDTO> getComments(int belong_id, String belong_type,int loginUserId)
    {
        //把内容的id和类型，绑定到querywrapper中，查询出内容下评论的列表。
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("belong_id",belong_id);
        queryWrapper.eq("belong_type",belong_type);
        List<Comment> list = this.list(queryWrapper);

        //光有comment的记录是不够的，还需要comment的创建者的部分信息，还要判断该评论是否被已登录用户点赞。
        List<CommentDTO> res=new ArrayList<>();
        for (Comment comment :list) {
            //这个userId指的评论的创建用户。
            int userId = comment.getUserId();
            Long commentId = comment.getId();
            CommentDTO commentDTO = new CommentDTO();

            User oneUser = userImpl.getOneUser(userId);
            String nickName = oneUser.getNickName();
            String department = oneUser.getDepartment();
            String avatar=oneUser.getAvatar();

            commentDTO.setLiked(this.userLike("comment",loginUserId,commentId));




            commentDTO.setComment(comment);
            commentDTO.setDepartment(department);
            commentDTO.setNickName(nickName);
            commentDTO.setAvatar(avatar);
            res.add(commentDTO);


        }
        return res;
    }*/
    public boolean userLike(String type,int loginUserId,int typeId)
    {
        QueryWrapper<UserLike> likeQueryWrapper=new QueryWrapper<>();
        likeQueryWrapper.eq("type",type);
        likeQueryWrapper.eq("user_id",loginUserId);
        likeQueryWrapper.eq("type_id",typeId);
        UserLike one = userLikeImpl.getOne(likeQueryWrapper);
        if(one==null){
            return false;
        }
        return true;

    }
    public List<CommentDTO> getCommentList(String belongType, int belongId){

        List<CommentDTO> res=new ArrayList<>();

        //获取comment的记录

        //查询包装器
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("belong_id",belongId);
        queryWrapper.eq("belong_type",belongType);
        //单评论的列表
        List<Comment> list1 = this.list(queryWrapper);

        //将comment升级成commentDTO
        for (Comment comment:list1
             ) {
            CommentDTO commentDTO=new CommentDTO();
            //查询出创建这条评论的用户
            int userId = comment.getUserId();
            User oneUser = userImpl.getOneUser(userId);

            commentDTO.setComment(comment);
            commentDTO.setUser(oneUser);

            //获取评论列表
            List<ReplyDTO> replyDTOList = replyImpl.getReplyDTOList(comment.getId());
            commentDTO.setReplyDTOList(replyDTOList);

            
            res.add(commentDTO);

        }


        return res;





    }
}
