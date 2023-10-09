package com.cn.zooey.constant;

import java.time.Duration;

/**
 * @author Fengzl
 * @date 2023/9/7 15:16
 * @desc
 **/
public class GlobalConstant {
    /**
     * 请求路径前缀
     */
    public static final String API_PREFIX = "/api/v1";


    // region 登录认证
    /**
     * 传递 TOKEN 的 Header 名称
     */
    public static final String TOKEN_HEADER = "zooey-token";

    /**
     * 全局保存到redis的key
     */
    public static final String GLOBAL_TOKEN_KEY = "zooey_login";

    /**
     * 登录用户保存到redis的key前缀
     */
    public static final String TOKEN_KEY_PREFIX = "login_user_";

    /**
     * jwt设置的过期时间,24小时,过期后强制重新登录
     */
    public static final Duration JWT_EXPIRATION = Duration.ofHours(24);
    /**
     * redis存储的过期时间,15分钟,且有请求后自动刷新(15分钟内无操作请求,需重新登录)
     */
    public static final String TOKEN_KEY_EXPIRATION = "#60*15";
    // endregion


}
