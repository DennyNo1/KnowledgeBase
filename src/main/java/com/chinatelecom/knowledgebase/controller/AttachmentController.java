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


}
