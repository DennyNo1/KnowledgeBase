package com.chinatelecom.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.knowledgebase.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 10:57
 * @Description
 * @Version 1.0
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
