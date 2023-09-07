package com.cn.zooey.common.base.exception;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.zooey.common.base.result.ResCode;
import com.cn.zooey.common.base.result.ResResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * @Author Fengzl
 * @Date 2023/3/2 17:45
 * @Desc 全局异常处理类
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理自定义基础异常
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResResult<?> baseException(BaseException e) {
        log.warn("[BaseException] --- code -> {}, message -> {}", e.getCode(), e.getMessage());
        return ResResult.of(e.getCode(), e.getMessage());
    }

    /**
     * 统一处理自定义业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(SaasException.class)
    public ResResult<?> saasException(SaasException e) {
        log.warn("[SaasException] --- code -> {}, message -> {}", e.getCode(), e.getMessage());
        return ResResult.error(e.getMessage());
    }

    /**
     * 统一处理异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Throwable.class})
    ResResult<?> handleException(Throwable e) {
        int code = ResCode.PARAM_ILLEGAL.getCode();
        String msg = ResCode.PARAM_ILLEGAL.getMsg();

        // 异常处理
        if (e instanceof MissingServletRequestParameterException) {
            msg = MessageFormat.format("缺少参数{0}", ((MissingServletRequestParameterException) e).getParameterName());
        } else if (e instanceof ConstraintViolationException) {
            // 单个参数校验异常
            Set<ConstraintViolation<?>> sets = ((ConstraintViolationException) e).getConstraintViolations();
            if (CollectionUtils.isNotEmpty(sets)) {
                StringBuilder sb = new StringBuilder();
                sets.forEach(error -> {
                    if (error instanceof FieldError) {
                        sb.append(((FieldError) error).getField()).append(":");
                    }
                    sb.append(error.getMessage()).append(";");
                });
                msg = StringUtils.substring(sb.toString(), 0, msg.length() - 1);
            }
        } else if (e instanceof MethodArgumentNotValidException) {
            // post请求的对象参数校验异常
            List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            String validMsg = getValidExceptionMsg(errors);
            if (StringUtils.isNotBlank(validMsg)) {
                msg = validMsg;
            }
        } else if (e instanceof BindException) {
            // get请求的对象参数校验异常
            List<ObjectError> errors = ((BindException) e).getBindingResult().getAllErrors();
            String bindMsg = getValidExceptionMsg(errors);
            if (StringUtils.isNotBlank(bindMsg)) {
                msg = bindMsg;
            }
        } else {
            log.error("[系统异常] --- message -> {}", e.getMessage(), e);
            return ResResult.of(ResCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
        }
        log.warn("[参数校验异常] --- message -> {}", msg);
        // ILLEGAL
        return ResResult.of(code, msg);
    }


    private String getValidExceptionMsg(List<ObjectError> errors) {
        if (CollectionUtils.isNotEmpty(errors)) {
            StringBuilder sb = new StringBuilder();
            errors.forEach(error -> {
                if (error instanceof FieldError) {
                    sb.append(((FieldError) error).getField()).append(":");
                }
                sb.append(error.getDefaultMessage()).append(";");
            });
            String msg = sb.toString();
            msg = StringUtils.substring(msg, 0, msg.length() - 1);
            return msg;
        }
        return null;
    }

}
