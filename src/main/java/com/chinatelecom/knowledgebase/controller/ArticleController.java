package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.ArticleDTO;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.DTO.VideoDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.service.impl.ArticleImpl;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 15:11
 * @Description 注释同样去看VideoController，思路一模一样
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleImpl articleImpl;

    @Autowired
    CommentImpl commentImpl;


    @GetMapping("/some")
    public R<Page<ArticleDTO>> getArticles(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName
    ){
        Page<ArticleDTO> articles = articleImpl.getArticles(page, pageSize, queryName);
        //理论上不会有失败的数据
        return R.success(articles,"成功传输article分页数据");

    }
    @GetMapping()
    public R<List> getOneArticle(
            @RequestParam(name = "id",required = true) int id
    ){
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Article one = articleImpl.getOne(queryWrapper);
        ArrayList<Object> res = new ArrayList<>();
        res.add(one);
        List<CommentDTO> commentsList = commentImpl.getComments(one.getId(), "article");
        res.add(commentsList);
        return R.success(res,"成功传输文章和其的评论");


    }

}
