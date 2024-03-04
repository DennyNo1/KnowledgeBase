package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.entity.Image;
import com.chinatelecom.knowledgebase.service.impl.ImageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Denny
 * @Date 2024/3/4 16:25
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageImpl imageImpl;
    @PostMapping("/add")
    public R addImage(@RequestBody Image image)
    {
        if(image.getImageUrl()==null)
            return R.error("上传失败，请输入上传的地址");
        boolean saveRes = imageImpl.save(image);
        if (saveRes) {
            return R.success(null, "上传成功");
        } else {
            throw new RuntimeException("上传失败，可能是由于数据库操作异常");
        }
    }
}
