package com.chinatelecom.knowledgebase.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Denny
 * @Date 2024/1/17 14:20
 * @Description 返回消息的类
 * @Version 1.0
 */
@Data//为了能序列化
public class R <T>{
    private Integer code; //编码：200成功，400等其它数字为失败

    private String msg; //错误信息

    private T data; //根据需求，传入任意类型的数据

    private Map map = new HashMap(); //这个应该是附带的额外的数据

    //成功
    public static <T> R<T> success(T object,String msg) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;//默认成功代码200
        r.msg=msg;
        return r;
    }

    //失败
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 400;//默认失败代码400
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
