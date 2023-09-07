package com.cn.zooey.common.base.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author Fengzl
 * @Date 2023/3/2 16:28
 * @Desc 状态码 枚举
 **/
@Getter
@AllArgsConstructor
public enum ResCode {

    /**
     * 成功
     */
    OK(200, "success"),
    /**
     * 失败
     */
    FAIL(400, "fail"),
    /**
     * 未授权
     */
    UNAUTHORIZED(401, "unauthorized"),
    /**
     * 接口不存在
     */
    NOT_FOUND(404, "interface not found"),
    /**
     * 参数非法
     */
    PARAM_ILLEGAL(4001, "param illegal"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "internal server error");

    private final int code;

    private final String msg;


    /**
     * 根据code获取ResCode
     * @param code
     * @return
     */
    public static ResCode getResCode(int code) {
        for (ResCode resCode : ResCode.values()) {
            if (Objects.equals(code, resCode.code)) {
                return resCode;
            }
        }
        return null;
    }

    // ====================================== HTTP 返回状态码说明 ===================================== //
    
      // 1 消息
      // ▪ 100 Continue
      // ▪ 101 Switching Protocols
      // ▪ 102 Processing
      //
      // 2 成功
      // ▪ 200 OK
      // ▪ 201 Created
      // ▪ 202 Accepted
      // ▪ 203 Non-Authoritative Information
      // ▪ 204 No Content
      // ▪ 205 Reset Content
      // ▪ 206 Partial Content
      // ▪ 207 Multi-Status
      //
      // 3 重定向
      // ▪ 300 Multiple Choices
      // ▪ 301 Moved Permanently
      // ▪ 302 Move Temporarily
      // ▪ 303 See Other
      // ▪ 304 Not Modified
      // ▪ 305 Use Proxy
      // ▪ 306 Switch Proxy
      // ▪ 307 Temporary Redirect
      //
      // 4 请求错误
      // ▪ 400 Bad Request
      // ▪ 401 Unauthorized
      // ▪ 402 Payment Required
      // ▪ 403 Forbidden
      // ▪ 404 Not Found
      // ▪ 405 Method Not Allowed
      // ▪ 406 Not Acceptable
      // ▪ 407 Proxy Authentication Required
      // ▪ 408 Request Timeout
      // ▪ 409 Conflict
      // ▪ 410 Gone
      // ▪ 411 Length Required
      // ▪ 412 Precondition Failed
      // ▪ 413 Request Entity Too Large
      // ▪ 414 Request-URI Too Long
      // ▪ 415 Unsupported Media Type
      // ▪ 416 Requested Range Not Satisfiable
      // ▪ 417 Expectation Failed
      // ▪ 418 I'm a teapot
      // ▪ 421 Misdirected Request
      // ▪ 422 Unprocessable Entity
      // ▪ 423 Locked
      // ▪ 424 Failed Dependency
      // ▪ 425 Too Early
      // ▪ 426 Upgrade Required
      // ▪ 449 Retry With
      // ▪ 451 Unavailable For Legal Reasons
      //
      // 5 服务器错误
      // ▪ 500 Internal Server Error
      // ▪ 501 Not Implemented
      // ▪ 502 Bad Gateway
      // ▪ 503 Service Unavailable
      // ▪ 504 Gateway Timeout
      // ▪ 505 HTTP Version Not Supported
      // ▪ 506 Variant Also Negotiates
      // ▪ 507 Insufficient Storage
      // ▪ 509 Bandwidth Limit Exceeded
      // ▪ 510 Not Extended
      // ▪ 600 Unparseable Response Headers

    // =========================================================================== //
}
