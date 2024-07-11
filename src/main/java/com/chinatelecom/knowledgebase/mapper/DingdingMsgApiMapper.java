package com.chinatelecom.knowledgebase.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.knowledgebase.entity.DingdingMsgApi;
import org.apache.ibatis.annotations.Mapper;
//@DS("oracle")
@Mapper
public interface DingdingMsgApiMapper extends BaseMapper<DingdingMsgApi> {
}
