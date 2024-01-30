package com.chinatelecom.knowledgebase.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Denny
 * @Date 2024/1/23 16:53
 * @Description
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
//在Spring Boot中，全局异常处理器可以通过实现org.springframework.web.bind.annotation.ControllerAdvice注解的类来实现。
public class GlobalExceptionHandler
{
    // 处理MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)

    public ResponseEntity<?> handleMissingRequestParamException(MissingServletRequestParameterException ex) {
        String errorMessage = "缺少请求参数：" + ex.getParameterName();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // 处理其他自定义或框架级别的异常
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("数据完整性约束违反：", ex);
        return new ResponseEntity<>("数据操作时违反了数据库完整性约束，请检查输入数据的有效性", HttpStatus.CONFLICT);
    }

    // 处理未知运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("运行时错误：", ex);
        return new ResponseEntity<>("服务器内部发生错误，请稍后重试", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
