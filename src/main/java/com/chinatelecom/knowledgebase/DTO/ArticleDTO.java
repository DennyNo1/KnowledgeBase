package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.entity.Image;
import lombok.Data;

import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/18 21:37
 * @Description
 * @Version 1.0
 */
@Data
//单篇文章的详情页的类
public class ArticleDTO {
    private ArticleListDTO articleListDTO;

    private List<Attachment> attachmentList;

    //是否被已登录用户点赞
    private boolean isLiked;
}
