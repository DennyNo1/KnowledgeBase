package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.ArticleDTO;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.Video;
import com.chinatelecom.knowledgebase.mapper.ArticleMapper;
import com.chinatelecom.knowledgebase.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 11:00
 * @Description 这个类的注释可以去看UserImpl，思路一摸一样
 * @Version 1.0
 */
@Service
public class ArticleImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    UserImpl userImpl;
    public Page<ArticleDTO> getArticles(int page,int pageSize,String queryName)
    {
        Page<Article> articlePage = new Page<>(page, pageSize);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if(queryName!=null){
            articleQueryWrapper.like("title",queryName);
        }
        this.page(articlePage,articleQueryWrapper);

        Page<ArticleDTO> articleDTOPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(articlePage,articleDTOPage,"records");
        List<ArticleDTO> list=new ArrayList<>();

        for(Article article:articlePage.getRecords())
        {
            ArticleDTO articleDTO = this.getOneArticleDTO(article);
            list.add(articleDTO);
        }
        articleDTOPage.setRecords(list);
        return articleDTOPage;

    }

    //通过一个article记录，去查这条记录有关的user记录，并组合成一个集合。
    public ArticleDTO getOneArticleDTO(Article article)
    {
        ArticleDTO articleDTO=new ArticleDTO();
        int uploaderId = article.getUploaderId();
        User user = userImpl.getOneUser(uploaderId);
        articleDTO.setArticle(article);
        articleDTO.setDepartment(user.getDepartment());
        articleDTO.setNickName(user.getNickName());
        return articleDTO;
    }

}
