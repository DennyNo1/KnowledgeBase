package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.Chat;
import com.chinatelecom.knowledgebase.mapper.ChatMapper;
import com.chinatelecom.knowledgebase.service.ChatService;
import org.springframework.stereotype.Service;

/**
 * @Author Denny
 * @Date 2024/8/2 9:55
 * @Description
 * @Version 1.0
 */
@Service
public class ChatImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
}
