package com.chinatelecom.knowledgebase.DTO;

import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/4/1 15:13
 * @Description
 * @Version 1.0
 */
@Data
public class ImageDTO
{
    private String url;// 图片 src ，必须
    private String alt;// 图片描述文字，非必须
    private String href;// 图片的链接，非必须
}
