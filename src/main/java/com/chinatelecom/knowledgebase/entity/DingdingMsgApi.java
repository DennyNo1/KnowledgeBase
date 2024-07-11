package com.chinatelecom.knowledgebase.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@DS("oracle")
@Data
@TableName("WZDM.DINGDING_MSG_API")
public class DingdingMsgApi
{
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private String toNumber;
    private String msg;
    private Integer flg;
    private String inputTime;
    private String updateTime;
    private String updateDetail;

}
