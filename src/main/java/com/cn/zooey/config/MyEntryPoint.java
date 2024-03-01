package com.cn.zooey.config;

import com.alibaba.fastjson.JSONObject;
import com.cn.zooey.common.base.result.ResCode;
import com.cn.zooey.common.base.result.ResResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Fengzl
 * @date 2023/10/7 16:45
 * @desc
 **/
@Slf4j
public class MyEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.warn("[MyEntryPoint] - {} - {}", request.getRequestURI(), authException.getMessage());

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 组装未授权的消息返回体
        ResResult<?> resResult = new ResResult<>();
        resResult.setCode(ResCode.UNAUTHORIZED.getCode());
        resResult.setMsg(ResCode.UNAUTHORIZED.getMsg());
        resResult.setTimestamp(System.currentTimeMillis());
        // 直接提示前端认证错误
        out.write(JSONObject.toJSONString(resResult));
        out.flush();
        out.close();
    }
}
