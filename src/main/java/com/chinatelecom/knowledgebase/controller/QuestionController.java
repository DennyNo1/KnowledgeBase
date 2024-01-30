package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.QuestionDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Question;
import com.chinatelecom.knowledgebase.service.impl.QuestionImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author Denny
 * @Date 2024/1/22 16:13
 * @Description
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController
{
    @Autowired
    QuestionImpl questionImpl;
    //提问，就是添加一个新问题。逻辑比较简单，直接写在控制器里。
    @PostMapping("/add")
    public R addQuestion(@RequestBody Question question){
        try {
            if(question.getQuestionerId()==0||question.getTitle()==null)
                return R.error("请输入正确的提问者id，问题标题");
            boolean saveRes = questionImpl.save(question);
            //log.info(String.valueOf(saveRes) + "!!!!!!!!!!!!!!!!!!!!");

            if (saveRes) {
                return R.success(null, "添加问题成功");
            } else {
                throw new RuntimeException("添加问题失败，可能是由于数据库操作异常");
            }
        } catch (DuplicateKeyException e) {
            log.error("插入问题时发生主键或唯一键冲突：", e);
            return R.error("添加问题失败，该问题标题已存在，请输入不同的问题标题");
        }
/*        catch (DataIntegrityViolationException e) {//已经在全局异常处理器中被处理
            log.error("插入问题时违反了数据库完整性约束：", e);
            return R.error("添加问题失败，请在body中添加questionerId");
      }*/
/*        catch (SQLIntegrityConstraintViolationException e) {//SQLIntegrityConstraintViolationException在Spring框架下可能包装为DataIntegrityViolationException。
            // 处理其他数据库约束违规异常
            // 处理其他数据库约束违规异常
            log.error("插入问题时违反了数据库完整性约束：", e);
            return R.error("添加问题失败，可能是由于外键或其他数据库约束条件未满足，请检查请求数据的有效性");
        }*/
        catch (Exception e) {
            // 捕获未知的异常情况
            log.error("添加问题时发生未知错误：", e);
            return R.error("服务器内部错误，请稍后重试");
        }
    }

    @GetMapping("/some") R<Page<QuestionDTO>> getQuestions(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName
    ){
        Page<QuestionDTO> questions = questionImpl.getQuestions(page, pageSize, queryName);
        return R.success(questions,"成功传输question分页数据");

    }

    //查看一个问题的所有数据
    @GetMapping
    public R<List> getOneQuestion(
            @RequestParam(name = "id",required = true) int id,
            @RequestParam(name="userId") int loginUserId
            //如果确实想要处理由于缺少必选参数 id 而抛出的异常，通常情况下 Spring MVC 会提前捕获这个异常，并通过统一异常处理器（例如全局异常处理器 @ControllerAdvice 中的方法）来处理，而不是在每个方法内部都手动处理。
    ){
        int questionId=id;
        List oneQuestion = questionImpl.getOneQuestion(questionId,loginUserId);
        if(oneQuestion.size()==0)
            return R.error("查找不到该问题，请输入正确的id");
        return R.success(oneQuestion,"传输一个问题的数据成功");


    }
}
