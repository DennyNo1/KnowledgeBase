package com.chinatelecom.knowledgebase.DTO;

import com.chinatelecom.knowledgebase.entity.Question;
import com.chinatelecom.knowledgebase.entity.User;
import lombok.Data;

/**
 * @Author Denny
 * @Date 2024/1/23 11:36
 * @Description
 * @Version 1.0
 */
@Data
public class QuestionDTO
{
    private Question question;
    private User user;

}
