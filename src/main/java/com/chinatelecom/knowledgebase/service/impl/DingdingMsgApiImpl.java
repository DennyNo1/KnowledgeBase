package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.entity.DingdingMsgApi;
import com.chinatelecom.knowledgebase.mapper.DingdingMsgApiMapper;
import com.chinatelecom.knowledgebase.service.DingdingMsgApiService;
import com.chinatelecom.knowledgebase.util.UniqueSixDigitGenerator;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DS("oracle")
@Service
public class DingdingMsgApiImpl extends ServiceImpl<DingdingMsgApiMapper, DingdingMsgApi> implements DingdingMsgApiService {
    public void insertAndProcess(String phone) {

        //获取六位不重复验证码
        int verificationCode= UniqueSixDigitGenerator.generateUniqueSixDigitNumber();

        //规范时间格式
        // 获取当前时间
        LocalDateTime inputTime = LocalDateTime.now();

        // 创建 DateTimeFormatter 对象来定义所需的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 使用 formatter 将 LocalDateTime 对象转换为字符串
        String formattedInputTime = inputTime.format(formatter);
        // 准备要插入的数据
        DingdingMsgApi entity = new DingdingMsgApi();
        entity.setToNumber("15305807322");
        entity.setMsg("触点平台修改密码的验证码是:"+verificationCode);
        entity.setFlg(0);
        entity.setInputTime( formattedInputTime); // 或者使用其他方式获取当前时间

        // 执行插入操作
       this.save(entity);

        // 假设需要根据某些逻辑进一步处理，比如查询刚插入的数据，这里可以根据实际情况编写查询逻辑
        // 注意：如果需要基于刚插入的记录进行查询，可能需要考虑事务管理，确保查询能看到最新的数据
        // 但具体查询逻辑需根据实际需求定制，因为MP没有直接提供基于插入后数据的查询功能
    }
}
