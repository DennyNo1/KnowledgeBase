package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.ArticleDTO;
import com.chinatelecom.knowledgebase.DTO.ArticleListDTO;
import com.chinatelecom.knowledgebase.entity.*;
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
    @Autowired
    ImageImpl imageImpl;
    @Autowired
    AttachmentImpl attachmentImpl;

    public Page<ArticleListDTO> getArticleList(int page,int pageSize,String queryName)
    {

        Page<Article> articlePage = new Page<>(page, pageSize);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if(queryName!=null){
            articleQueryWrapper.like("title",queryName);
        }
        //根据页的条件，查询出article对象的page
        this.page(articlePage,articleQueryWrapper);

        Page<ArticleListDTO> articleListDTOPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(articlePage,articleListDTOPage,"records");
        List<ArticleListDTO> list=new ArrayList<>();

        for(Article article:articlePage.getRecords())
        {
            ArticleListDTO articleListDTO = this.getArticleListDTO(article);
            Article article1 = articleListDTO.getArticle();
            article1.setContent(null);
            list.add(articleListDTO);
        }
        articleListDTOPage.setRecords(list);
        return articleListDTOPage;

    }

    //通过一个article记录，去查这条记录有关的user记录，并组合成一个集合。
    public ArticleListDTO getArticleListDTO(Article article)
    {
        ArticleListDTO articleListDTO=new ArticleListDTO();
        int uploaderId = article.getUploaderId();
        User user = userImpl.getOneUser(uploaderId);
        articleListDTO.setArticle(article);

        articleListDTO.setDepartment(user.getDepartment());
        articleListDTO.setNickName(user.getNickName());
        articleListDTO.setRole(user.getRole());
        articleListDTO.setAvatar(user.getAvatar());
        return articleListDTO;
    }

    public ArticleDTO getArticleDTO(int articleId)
    {

        Article article = this.getById(articleId);
        ArticleListDTO articleListDTO = this.getArticleListDTO(article);

        ArticleDTO articleDTO=new ArticleDTO();
        articleDTO.setArticleListDTO(articleListDTO);

        List<Image> imageList = imageImpl.getImageList(articleId);
        List<Attachment> attachmentList = attachmentImpl.getAttachmentList(articleId);
        articleDTO.setImageList(imageList);
        articleDTO.setAttachmentList(attachmentList);

        return articleDTO;


    }

}
