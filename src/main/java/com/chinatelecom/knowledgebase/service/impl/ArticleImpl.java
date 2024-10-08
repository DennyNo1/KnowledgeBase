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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<ArticleListDTO> getArticleList(int page,int pageSize,String queryName,String type,String uploaderId,String queryUploader)
    {

        Page<Article> articlePage = new Page<>(page, pageSize);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if(uploaderId!=null&&!uploaderId.equals("")){
            articleQueryWrapper.eq("uploader_id",Integer.valueOf(uploaderId));
        }
        if(queryName!=null){
            articleQueryWrapper.like("title",queryName);

        }
        if(type!=null){
            if (type.equals("热门知识")) {
                // 先按click_count排序，再按date排序
                articleQueryWrapper.orderByDesc("click_count");
                articleQueryWrapper.orderByDesc("date");
            } else if (type.equals("默认")) {
                // 仅按date排序
                articleQueryWrapper.orderByDesc("date");
            } else {

                // 先按top排序，再按date排序
                articleQueryWrapper.and(wrapper -> wrapper.like("type", type).or().like("type", "*"));
                articleQueryWrapper.orderByDesc("top");
                articleQueryWrapper.orderByDesc("date");
            }
        }

        //查询上传者
        if(queryUploader!=null)
        {
            QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
            userQueryWrapper.like("nick_name", queryUploader);//可能存在重名
            List<User> list = userImpl.list(userQueryWrapper);
            if(list.size()>0)
            {
                List<Integer> userIds = list.stream()
                        .map(User::getId)
                        .collect(Collectors.toList());
                articleQueryWrapper.in("uploader_id",userIds);
            }
            //如果不存在这个用户
            else {
                articleQueryWrapper.eq("uploader_id",0);
            }



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

        //移除安全问题等敏感信息
        user.setSafeQuestion("");
        user.setSafeAnswer("");

       articleListDTO.setUser(user);
        return articleListDTO;
    }

    public ArticleDTO getArticleDTO(int articleId)
    {

        Article article = this.getById(articleId);

        //增加浏览量
        article.setClickCount(article.getClickCount()+1);
        this.updateById(article);

        ArticleListDTO articleListDTO = this.getArticleListDTO(article);

        ArticleDTO articleDTO=new ArticleDTO();
        articleDTO.setArticleListDTO(articleListDTO);


        List<Attachment> attachmentList = attachmentImpl.getAttachmentList(articleId);

        articleDTO.setAttachmentList(attachmentList);

        return articleDTO;


    }
    public boolean likeArticle(Integer articleId){
        //给article表的likeCount+1

        //通过id查出该记录
        try {
            //我感觉只会在这一步出现异常
            Article byId = this.getById(articleId);
            int likeCount = byId.getLikeCount();
            byId.setLikeCount(likeCount+1);

            this.saveOrUpdate(byId);
            return true;

        } catch (Exception e) {
            throw new RuntimeException("无法查询到该文章记录"+e);
        }


    }


}
