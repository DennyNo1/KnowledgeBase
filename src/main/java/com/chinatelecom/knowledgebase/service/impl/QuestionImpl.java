package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.QuestionDTO;
import com.chinatelecom.knowledgebase.entity.*;
import com.chinatelecom.knowledgebase.mapper.QuestionMapper;
import com.chinatelecom.knowledgebase.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
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
    public Page<QuestionDTO> getQuestionList(int page, int pageSize, String queryName, int isChecked,String type){
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
            else queryWrapper.eq("type",type);
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
