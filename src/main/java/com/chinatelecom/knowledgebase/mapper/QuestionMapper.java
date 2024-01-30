package com.chinatelecom.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.knowledgebase.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Denny
 * @Date 2024/1/22 16:11
 * @Description
 * @Version 1.0
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
