package com.cn.zooey.filter;

import com.alibaba.fastjson.JSONObject;
import com.cn.zooey.cache.DataCache;
import com.cn.zooey.constant.GlobalConstant;
import com.cn.zooey.dto.LoginUser;
import com.cn.zooey.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Fengzl
 * @date 2023/10/7 11:37
 * @desc JWT限制24小时强制重新登录, Redis缓存限制15分钟内无操作重新登录, SQL验证用户数据是否状态正常
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final DataCache dataCache;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1、从请求头中获取token，如果请求头中不存在token，直接放行即可！由Spring Security的过滤器进行校验！
        String token = request.getHeader(GlobalConstant.TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            // log.warn("[JwtAuthenticationTokenFilter] - 无token -> {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        // 2、对token进行解析，取出其中的userId
        long userId;
        try {
            token = token.replace(GlobalConstant.TOKEN_HEADER_PREFIX, "");
            // 校验JWT的token是否过期
            Claims claims = JWTUtil.parse(token);
            if (Objects.isNull(claims)) {
                log.warn("[JwtAuthenticationTokenFilter] - JWT解析为null");
                // 放行
                filterChain.doFilter(request, response);
                return;
            }
            String subject = claims.getSubject();
            userId = Long.parseLong(subject);
        } catch (Exception e) {
            log.warn("[JwtAuthenticationTokenFilter] - JWT解析失败");
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 验证缓存
        String loginUserJson = dataCache.getLoginToken(GlobalConstant.GLOBAL_TOKEN_KEY, GlobalConstant.TOKEN_KEY_PREFIX + userId);
        LoginUser loginUser = JSONObject.parseObject(loginUserJson, LoginUser.class);

        if (Objects.nonNull(loginUser)) {
            // 验证token
            if (!Objects.equals(token, loginUser.getToken())) {
                log.warn("[JwtAuthenticationTokenFilter] - token不是最新");
                // 放行
                filterChain.doFilter(request, response);
                return;
            }
            // 验证用户信息
            try {
                userDetailsService.loadUserByUsername(loginUser.getUsername());
            } catch (Exception e) {
                log.warn("[JwtAuthenticationTokenFilter] - 用户状态异常");
                // 放行
                filterChain.doFilter(request, response);
                return;
            }
            // 表示验证登录权限成功
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 表示授权成功, 更新过期时间(自动续期)
            dataCache.setLoginToken(GlobalConstant.GLOBAL_TOKEN_KEY, GlobalConstant.TOKEN_KEY_PREFIX + loginUser.getId(), JSONObject.toJSONString(loginUser));
            // redisTemplate.expire(GlobalConstant.TOKEN_KEY_PREFIX + loginUser.getId(), GlobalConstant.TOKEN_KEY_EXPIRATION);
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
