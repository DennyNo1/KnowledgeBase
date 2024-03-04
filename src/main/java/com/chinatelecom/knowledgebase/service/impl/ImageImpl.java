package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.Image;
import com.chinatelecom.knowledgebase.mapper.ImageMapper;
import com.chinatelecom.knowledgebase.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Denny
 * @Date 2024/3/4 11:00
 * @Description
 * @Version 1.0
 */
@Service
public class ImageImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    public List<Image> getImageList(int articleId)
    {
        QueryWrapper<Image> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        List<Image> list = this.list(queryWrapper);
        return list;

    }
}
