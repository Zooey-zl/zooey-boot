package com.cn.zooey.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.List;

/**
 * @author Fengzl
 * @date 2023/8/30 16:34
 * @desc
 **/
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 通过 FilterRegistrationBean 注册 CorsFilter
     * <br/>解决 Cors 跨域
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        // 跨域 Filter
        CorsFilter corsFilter = new CorsFilter(request -> {
            // 请求源
            String origin = request.getHeader(HttpHeaders.ORIGIN);
            // 非跨域请求
            if (!StringUtils.hasText(origin)) {
                return null;
            }

            // 针对每个请求, 编程式设置跨域
            CorsConfiguration config = new CorsConfiguration();
            // 允许发起跨域请求的源, 直接去 Origin header 值, 不论源是哪儿, 服务器都接受
            config.addAllowedOrigin(origin);
            // 允许客户端的所有请求 Header
            String headers = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
            if (StringUtils.hasText(headers)) {
                config.addAllowedHeader(headers);
            }
            // 允许客户端的所有请求方法
            config.addAllowedMethod(request.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS));

            // 允许读取所有 Header
            // 注意, "*" 通配符, 可能在其他低版本浏览器中不兼容
            config.addExposedHeader("*");
            // 缓存30分钟
            config.setMaxAge(Duration.ofMinutes(30));
            // 允许携带凭证
            config.setAllowCredentials(Boolean.TRUE);

            return config;
        });

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        // Filter拦截路径
        bean.addUrlPatterns("/api/*");
        // 保证最先执行
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);

        return bean;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        messageConverter.setObjectMapper(objectMapper);
        // converters.add(0, messageConverter); 会导致 knife4j 页面报错,修改为converters.size() -1 解决
        converters.add(converters.size() -1, messageConverter);
    }
}
