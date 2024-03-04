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
public class ArticleDTO {
    private ArticleListDTO articleListDTO;
    //简单的话其实只用imageUrl。但为了后续的扩展，还是设置为对象。
    private List<Image> imageList;
    private List<Attachment> attachmentList;
}
