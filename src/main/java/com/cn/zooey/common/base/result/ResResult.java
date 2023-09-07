package com.cn.zooey.common.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Fengzl
 * @Date 2023/3/2 16:01
 * @Desc 全部接口返回体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResResult<T> implements Serializable {
    private static final long serialVersionUID = 7241208732179772344L;

    private Integer code;
    private T data;
    private String msg;
    private long timestamp;


    public static <T> ResResult<T> ok() {
        return of(ResCode.OK.getCode(), null, ResCode.OK.getMsg());
    }

    public static <T> ResResult<T> ok(String msg) {
        return of(ResCode.OK.getCode(), null, msg);
    }

    public static <T> ResResult<T> ok(T data) {
        return of(ResCode.OK.getCode(), data, ResCode.OK.getMsg());
    }

    public static <T> ResResult<T> ok(T data, String msg) {
        return of(ResCode.OK.getCode(), data, msg);
    }

    public static <T> ResResult<T> error() {
        return of(ResCode.FAIL.getCode(), null, ResCode.FAIL.getMsg());
    }

    public static <T> ResResult<T> error(String msg) {
        return of(ResCode.FAIL.getCode(), null, msg);
    }

    public static <T> ResResult<T> error(T data) {
        return of(ResCode.FAIL.getCode(), data, ResCode.FAIL.getMsg());
    }

    public static <T> ResResult<T> error(T data, String msg) {
        return of(ResCode.FAIL.getCode(), data, msg);
    }


    public static <T> ResResult<T> of(Integer code, T data, String msg) {
        return new ResResult<>(code, data, msg, System.currentTimeMillis());
    }

    public static <T> ResResult<T> of(Integer code, String msg) {
        return new ResResult<>(code, null, msg, System.currentTimeMillis());
    }


}
