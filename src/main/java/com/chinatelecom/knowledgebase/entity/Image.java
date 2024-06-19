package com.chinatelecom.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/3/1 11:40
 * @Description 因为一个实体类对应一张表，所以我把设计思想写在这里比较合适。这个类，开始是为了映射文章中的图片存在的，但现在已经没必要了。
 * 因为图片的url，经过后端返回给前端，前端的富文本编辑器，已经将其保存在了文章的html元素中。所以它想过的mapper，service，serviceImpl都是无用的。
 * 同样的设计思想，应用于文章中的视频。
 * 但是文章的附件，是需要一个实体类，同样也需要一张表。因为附件需要被下载。
 * @Version 1.0
 */
@Data
public class Image {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private String url;
    private String alt;// 图片描述文字，非必须
    private String href;// 图片的链接，非必须
}
