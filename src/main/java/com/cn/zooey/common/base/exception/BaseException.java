package com.cn.zooey.common.base.exception;

import com.cn.zooey.common.base.result.ResCode;
import lombok.Getter;

/**
 * @Author Fengzl
 * @Date 2023/3/2 17:24
 * @Desc 自定义异常基类 默认是系统内部异常
 **/
@Getter
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 7364024520977822221L;

    private static final int EXCEPTION_CODE = ResCode.INTERNAL_SERVER_ERROR.getCode();
    private static final String EXCEPTION_MESSAGE = ResCode.INTERNAL_SERVER_ERROR.getMsg();

    private final int code;

    private final String message;

    public BaseException() {
        super(EXCEPTION_MESSAGE);
        this.code = EXCEPTION_CODE;
        this.message = EXCEPTION_MESSAGE;
    }

    public BaseException(String message) {
        super(message);
        this.code = EXCEPTION_CODE;
        this.message = message;
    }

    public BaseException(ResCode resCode) {
        super(resCode.getMsg());
        this.code = resCode.getCode();
        this.message = resCode.getMsg();
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
