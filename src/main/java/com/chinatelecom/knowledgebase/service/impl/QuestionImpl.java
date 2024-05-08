package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.QuestionDTO;
import com.chinatelecom.knowledgebase.entity.*;
import com.chinatelecom.knowledgebase.mapper.QuestionMapper;
import com.chinatelecom.knowledgebase.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/22 16:12
 * @Description
 * @Version 1.0
 */

@Slf4j
@Service
public class QuestionImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService
{
    @Autowired
    UserImpl userImpl;
    @Autowired
    ReplyImpl replyImpl;
    @Autowired
    CommentImpl commentImpl;
    //mybatisplus竟然不支持连表查询
    public Page<QuestionDTO> getQuestionList(int page, int pageSize, String queryName, int isChecked,String type,String assignTo){
        Page<Question> questionPage=new Page<>(page,pageSize);
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_checked",isChecked);
        if(queryName!=null){
            queryWrapper.like("title",queryName);
        }
        if(type!=null)
        {
            if(type.equals("热门知识"))
            {
                queryWrapper.orderByDesc("click_count");
            }
            //问题列表和文章列表，都是按时间降序。但评论和回复，一般是按时间升序。
            else if(type.equals("默认")){
                queryWrapper.orderByDesc("date");
            }
            else queryWrapper.eq("type",type);
        }
        if(assignTo!=null)
        {
            if(!assignTo.equals("admin"))
            {
                queryWrapper.eq("assign_to",assignTo);

            }
            //查未被解决的问题
            queryWrapper.eq("is_solved",0);

        }


        //查询。把查询结果放到page中。
        this.page(questionPage,queryWrapper);

        //最终结果
        Page<QuestionDTO> questionDTOPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(questionPage,questionDTOPage,"records");

        List<QuestionDTO> list=new ArrayList<>();

        for(Question question:questionPage.getRecords())
        {
            QuestionDTO questionDTO = this.getPartOfUser(question);
            list.add(questionDTO);
        }
        questionDTOPage.setRecords(list);
        return questionDTOPage;

    }

    public QuestionDTO getQuestionDTO(int questionId)
    {
        Question byId = this.getById(questionId);
        //每次访问更新点击量
        byId.setClickCount(byId.getClickCount()+1);
        this.updateById(byId);

       return this.getPartOfUser(byId);



    }
    public QuestionDTO getPartOfUser( Question question){
        int questionerId = question.getQuestionerId();
        User oneUser = userImpl.getOneUser(questionerId);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        questionDTO.setUser(oneUser);
        return questionDTO;
    }

    //查询未回复的问题的数量
    public Integer getNumOfQuestionWithoutComment(String role){
        QueryWrapper<Question> queryWrapper=new QueryWrapper();
        int count=0;
        //假设role是正确的
        if(!role.equals("admin"))
        {
            queryWrapper.eq("assign_to",role);



        }
        //所有通过审核的问题，都是慧问待回答的问题
        else {
            queryWrapper.eq("is_checked",1);


        }
        //这个管理员需要回复的问题列表
        List<Question> list = this.list(queryWrapper);
        count=list.size();
        for (Question question: list
        ) {
            Integer id = question.getId();
            //判断这个问题的回复是否存在
            QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
            commentQueryWrapper.eq("belong_type",question);
            commentQueryWrapper.eq("belong_id",id);
            Comment one = commentImpl.getOne(commentQueryWrapper);
            if(one!=null) count--;


        }
        return count;
    }



    /*//查询一个问题的数据
    public List getOneQuestion(int questionId,int loginUserId){
        //问题+回答+评论的列表
        List res=new ArrayList();


        //还是把问题本身的数据再传一遍
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",questionId);
        Question one = this.getOne(queryWrapper);
        //如果输入一个不存在的id来查询question
        if(one==null)
            return res;


        res.add(one);

        //查问题对应的回复
        QueryWrapper<Reply> replyQueryWrapper=new QueryWrapper<>();
        replyQueryWrapper.eq("question_id",questionId);
        //所有回答的列表，仅有回答
        List<Reply> list = replyImpl.list(replyQueryWrapper);
        //包含回答和评论的列表
        List allReply=new ArrayList();




        //再由每个回复，去查回复下的评论
        for (Reply r:list
             )
        {
            List oneReply=new ArrayList();
            ReplyDTO replyDTO=new ReplyDTO();
            replyDTO.setReply(r);
            int replyId=r.getId();
            User oneUser = userImpl.getOneUser(r.getAuthorId());
            replyDTO.setUsername(oneUser.getUsername());
            replyDTO.setAvatar(oneUser.getAvatar());

            //查询这个reply是否被已登录用户点赞
            replyDTO.setLike(commentImpl.userLike("reply",loginUserId,replyId));



            List<CommentDTO> commentList = commentImpl.getComments(replyId, "reply", loginUserId);



            oneReply.add(replyDTO);
            oneReply.add(commentList);
            allReply.add(oneReply);

        }
        res.add(allReply);
        return res;

    }*/
}
