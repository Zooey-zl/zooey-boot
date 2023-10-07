package com.cn.zooey.filter;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.zooey.common.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static com.cn.zooey.common.base.log.LogConstant.REQUEST_ID;
import static com.cn.zooey.common.base.log.LogConstant.REQUEST_ID_PREFIX;


/**
 * @Author Fengzl
 * @Date 2023/3/3 23:29
 * @Desc
 **/
@Slf4j
@WebFilter(urlPatterns = {"/*"}, filterName = "RequestWrapperFilter")
@Order(value = 1)
public class RequestWrapperFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest requestM = (HttpServletRequest) servletRequest;
        // 过滤请求路径
        if (!isFilterExcludeRequest(requestM)) {
            RequestBodyWrapper request = new RequestBodyWrapper(requestM);
            String requestUuid = REQUEST_ID_PREFIX + System.currentTimeMillis() + RandomUtil.randomNumbers(2);
            MDC.put(REQUEST_ID, requestUuid);
            request.setAttribute(REQUEST_ID, requestUuid);
            String strParams;
            if (request.isEmpty()) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                strParams = JSONObject.toJSONString(parameterMap);
            } else {
                strParams = request.getRequestParams();
            }
            // 打印请求日志
            printRequestLog(request, strParams);

            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(requestM, servletResponse);
        }
    }

    /**
     * 打印请求日志
     * @param request
     * @param strParams
     */
    private static void printRequestLog(RequestBodyWrapper request, String strParams) {
        log.info("-----------------------------------------------------------");
        log.info("                                                           ");
        log.info(" ## request method -> {}", request.getMethod().toUpperCase());
        log.info(" ## request uri -> {}", request.getRequestURI());
        log.info(" ## request ip -> {}", IPUtils.getClientIp(request));
        log.info(" ## request params -> {}", strParams);
        log.info(" ## request ContentType -> {}", request.getContentType());
        log.info("                                                           ");
        log.info("-----------------------------------------------------------");
    }


    /**
     * 过滤特殊路径
     * @param request
     * @return
     */
    public boolean isFilterExcludeRequest(HttpServletRequest request) {
        String strUri = request.getRequestURI();
        return strUri.equals("/") || strUri.startsWith("/webjars") || strUri.endsWith(".ico")
                || strUri.endsWith(".html") || strUri.startsWith("/v3/api-docs") || strUri.startsWith("/druid")
                || strUri.startsWith("/prometheus") || strUri.startsWith("/actuator");
    }
}
