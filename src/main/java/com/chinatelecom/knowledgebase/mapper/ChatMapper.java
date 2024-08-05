package com.chinatelecom.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.knowledgebase.entity.Chat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Denny
 * @Date 2024/8/2 9:54
 * @Description
 * @Version 1.0
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
}
