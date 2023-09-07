package com.cn.zooey.common.base.exception;


import com.cn.zooey.common.base.result.ResCode;

/**
 * @Author Fengzl
 * @Date 2023/3/2 17:41
 * @Desc 自定义业务异常
 **/
public class SaasException extends BaseException {
    private static final long serialVersionUID = -8347496142734272871L;

    private static final int EXCEPTION_CODE = ResCode.FAIL.getCode();
    private static final String EXCEPTION_MESSAGE = ResCode.FAIL.getMsg();

    public SaasException() {
        super(EXCEPTION_CODE, EXCEPTION_MESSAGE);
    }

    public SaasException(String message) {
        super(EXCEPTION_CODE, message);
    }
}
