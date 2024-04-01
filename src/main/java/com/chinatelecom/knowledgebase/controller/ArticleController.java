package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.ArticleDTO;
import com.chinatelecom.knowledgebase.DTO.ArticleListDTO;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.DTO.VideoDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.Video;
import com.chinatelecom.knowledgebase.service.impl.ArticleImpl;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    UserImpl userImpl;

    //获取文章列表
    @GetMapping("/some")
    public R<Page<ArticleListDTO>> getArticleList(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName,
            @RequestParam(name="type",required = false) String type
    ){
        Page<ArticleListDTO> articleList = articleImpl.getArticleList(page, pageSize, queryName,type);
        //理论上不会有失败的数据
        return R.success(articleList,"成功传输article分页数据");

    }


    //
    @GetMapping()
    public R<ArticleDTO> getArticle(
            @RequestParam(name = "articleId",required = true) int articleId

    ){
        //查询到一篇文章的记录
        ArticleDTO articleDTO = articleImpl.getArticleDTO(articleId);


        return R.success(articleDTO,"成功传输课件");

    }

    @PostMapping("/add")
    public R addArticle(@RequestBody Article article)
    {
        if(article.getContent()==null||article.getTitle()==null)
            return R.error("上传失败，请输入文章的标题、内容");
        boolean saveRes = articleImpl.save(article);
        if (saveRes) {
            return R.success(null, "上传成功");
        } else {
            throw new RuntimeException("上传失败，可能是由于数据库操作异常");
        }
    }


}
