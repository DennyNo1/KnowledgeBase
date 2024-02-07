package com.chinatelecom.knowledgebase.DTO;

import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/2/7 16:22
 * @Description
 * @Version 1.0
 */
//这是个人中心，所需要数据的映射对象。目前只做，提问和回答的数量。
@Data
public class UserCenterDTO
{
    private int questionCount;
    private int replyCount;

}
