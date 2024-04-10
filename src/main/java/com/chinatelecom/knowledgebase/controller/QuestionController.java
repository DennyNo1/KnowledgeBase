package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.QuestionDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Question;
import com.chinatelecom.knowledgebase.service.impl.QuestionImpl;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    @Autowired
    UserImpl userImpl;
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

    @GetMapping("/some")
    public R<Page<QuestionDTO>> getQuestionList(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName,
            @RequestParam(name="isChecked",required = false,defaultValue = "1") int isChecked,
            @RequestParam(name="type",required = false) String type

    ){
        if(type.equals("默认")) type=null;

        Page<QuestionDTO> questions = questionImpl.getQuestionList(page, pageSize, queryName,isChecked,type);
        return R.success(questions,"成功传输question分页数据");

    }

    @PostMapping("/check")
    public R check(@RequestBody Map<String,Integer> data){

        Integer questionId = data.get("questionId");
        Integer isChecked = data.get("isChecked");
        if(isChecked<-1||isChecked>1)
            return R.error("审核结果值有误，请重新设置");
        try {
        //先查再改
        Question newQuestion = questionImpl.getById(questionId);
        newQuestion.setIsChecked(isChecked);

            questionImpl.updateById(newQuestion);
            return R.success(null,"审核结果更新成功");
        }
        catch (Exception e)
        {
            return R.error("审核结果更新失败，可能由于该问题不存在");

        }

    }
    //返回一个list，里面包含questiondto和它的评论区
    @GetMapping()
    public R<QuestionDTO> getQuestion(@RequestParam(name = "questionId",required = true) int questionId){

        QuestionDTO questionDTO = questionImpl.getQuestionDTO(questionId);
        return R.success(questionDTO,"传输问题相关数据成功");


    }



}
