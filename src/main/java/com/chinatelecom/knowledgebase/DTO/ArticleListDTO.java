package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.User;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/3/4 10:15
 * @Description
 * @Version 1.0
 */
@Data
public class ArticleListDTO
{
    private Article article;
    private User user;
}
