package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.mapper.AttachmentMapper;
import com.chinatelecom.knowledgebase.service.AttachmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Denny
 * @Date 2024/3/4 11:02
 * @Description
 * @Version 1.0
 */
@Service
public class AttachmentImpl extends ServiceImpl<AttachmentMapper, Attachment>  implements AttachmentService {
    public  List<Attachment> getAttachmentList(int articleId){
        QueryWrapper<Attachment> attachmentQueryWrapper=new QueryWrapper<>();
        attachmentQueryWrapper.eq("article_id",articleId);
        //可能会返回多个对象，所以返回一个list
        List<Attachment> list = this.list(attachmentQueryWrapper);
        return list;
    }
}
