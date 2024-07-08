package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
            @RequestParam(name="isChecked",required = false,defaultValue = "1") int isChecked,//审核已经填了默认值
            @RequestParam(name="type",required = false ) String type,
            @RequestParam(name="assignTo",required = false) String assignTo,
            @RequestParam(name="isSolved",required = false) String isSolved,
            @RequestParam(name="queryUploader",required = false)String queryUploader

    ){


        Page<QuestionDTO> questions = questionImpl.getQuestionList(page, pageSize, queryName,isChecked,type,assignTo,isSolved,queryUploader);
        return R.success(questions,"成功传输question分页数据");

    }
    //处理问题审核的逻辑。派单

    @PostMapping("/check")
    public R check(@RequestBody Map<String,Object> data){

        Integer questionId = (Integer) data.get("questionId");
        Integer isChecked = (Integer) data.get("isChecked");
        //分配给谁是必须存在的
        String assignTo= (String) data.get("assignTo");
        if(isChecked<-1||isChecked>1)
            return R.error("审核结果值有误，请重新设置");
        try {
        //先查再改
        Question newQuestion = questionImpl.getById(questionId);
        newQuestion.setIsChecked(isChecked);
            newQuestion.setAssignTo(assignTo);

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

    //这个接口用于返回待审核问题的数量和待回复问题的数量
    @GetMapping("/number")
    public R<Integer> getQuestionNumber(@RequestParam(name = "questionType",required = true) String questionType,@RequestParam(name = "role",required = true) String role){
        //待审核问题数量
        if(questionType.equals("check")){
            try{
                QueryWrapper<Question> questionQueryWrapper=new QueryWrapper<>();
                questionQueryWrapper.eq("is_checked",0);
                int count = questionImpl.count(questionQueryWrapper);
                return R.success(count,"传输待审核问题数量成功");
            }
            catch (Exception e)
            {
                return R.error("数据库报错");
            }
        }
        if(questionType.equals("comment"))
        {
            try{
                //查询未回复的问题的数量
                Integer count = questionImpl.getNumOfQuestionWithoutComment(role);
                return R.success(count,"传输待回复问题数量成功");

            }
            catch (Exception e)
            {
                return R.error("数据库报错");
            }
        }
        return R.error("问题类型有误");

    }
    //这个方法用于返回是否该用户是否有待回复的问题，待审核的问题
    //问题是分配给角色的
    @GetMapping("/wait")
    public R getHandleQuestion(@RequestParam(name = "role",required = true) String role){
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        //如果是管理员，查是否有待审核问题和待回复问题
        if(role.equals("admin")){

            queryWrapper.or().eq("is_checked",0);
            queryWrapper.or().eq("is_solved",0);
        }
        else{
            queryWrapper.eq("is_solved",0);
            queryWrapper.eq("assign_to",role);
        }
        int count = questionImpl.count(queryWrapper);
        //这里只有查询，应该不会报其他的异常
        if(count>0)
            return R.success(true,"有待处理需求");
        else return R.success(false,"无待处理需求");


    }




}
