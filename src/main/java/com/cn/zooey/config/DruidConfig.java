package com.cn.zooey.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Fengzl
 * @Date 2023/7/31 11:43
 * @Desc
 **/
@Slf4j
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean() {

        log.info("init Druid Monitor Servlet...");
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> initMap = new HashMap<>();
        initMap.put("loginUsername", "admin");
        initMap.put("loginPassword", "admin");
        initMap.put("resetEnable", "false");
        // 默认就是允许所有访问 IP白名单 (没有配置或者为空，则允许所有访问)
        // initMap.put("allow", "");
        // IP黑名单 (存在共同时，deny优先于allow)
        // initMap.put("deny", "");

        servletRegistrationBean.setInitParameters(initMap);
        servletRegistrationBean.setEnabled(true);

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        log.info("Druid Filter...");
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(filterMap);

        // filterRegistrationBean.addInitParameter("profileEnable", "true");
        // filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        // filterRegistrationBean.addInitParameter("principalSessionName", "");
        // filterRegistrationBean.addInitParameter("aopPatterns", "com.example.demo.service");

        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.setEnabled(true);

        return filterRegistrationBean;

    }
}
