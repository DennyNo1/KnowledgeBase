package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Article;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/18 21:37
 * @Description
 * @Version 1.0
 */
@Data
public class ArticleDTO {
    private Article article;
    private String nickName;
    private String department;
    private String avatar;
    private String role;
}
