package com.chinatelecom.knowledgebase.common;

import com.chinatelecom.knowledgebase.DTO.ImageDTO;
import com.chinatelecom.knowledgebase.entity.Image;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/4/1 15:07
 * @Description 给富文本编辑器的图片上传的特定response body
 * @Version 1.0
 */
@Data

public class RofImg{
    private Integer errno; //编码：1成功， 0为失败
    private String message; //错误信息

    private ImageDTO data;

    //成功
    public static  RofImg success(com.chinatelecom.knowledgebase.DTO.ImageDTO imageDTO) {
        RofImg r = new RofImg();
        r.data=imageDTO;
        r.errno=1;
        return  r;
    }
    //失败
    public static RofImg error(String msg){
        RofImg r = new RofImg();
        r.errno=0;
        r.message=msg;
        return  r;
    }
}
