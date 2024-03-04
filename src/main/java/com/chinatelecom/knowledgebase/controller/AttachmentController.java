package com.chinatelecom.knowledgebase.controller;

import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.service.impl.AttachmentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Denny
 * @Date 2024/3/4 15:55
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    AttachmentImpl attachmentImpl;
    @GetMapping()
    public R<String> getAttachmentUrl(
            @RequestParam(name = "attachmentId",required = true) String attachmentId
    ){
        Attachment attachment = attachmentImpl.getById(attachmentId);
        return R.success(attachment.getAttachmentUrl(),"成功传输附件url");

    }
    @PostMapping("/add")
        public R addAttachment(@RequestBody Attachment attachment)
        {
            if(attachment.getAttachmentUrl()==null)
                return R.error("上传失败，请输入上传的地址");
            boolean saveRes = attachmentImpl.save(attachment);
            if (saveRes) {
                return R.success(null, "上传成功");
            } else {
                throw new RuntimeException("上传失败，可能是由于数据库操作异常");
            }
        }
}
