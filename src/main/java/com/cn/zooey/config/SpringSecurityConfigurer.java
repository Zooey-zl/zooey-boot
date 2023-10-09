package com.cn.zooey.config;

import com.cn.zooey.filter.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * @author Fengzl
 * @date 2023/10/7 11:10
 * @desc
 **/
@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        // 这里我们使用bcrypt加密算法，安全性比较高
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                // 关闭csrf
                .csrf().disable()
                // 关闭frameOptions
                .headers().frameOptions().disable()
                .and()
                // 指定session的创建策略,不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 再次获取到HttpSecurity对象
                .and()
                // 进行认证请求的配置
                .authorizeRequests()
                // 对于登录接口, 允许匿名访问(走特定的拦截器)
                .antMatchers(API_PREFIX + "/login/verifier").anonymous()
                // 放行一下接口(不走拦截器)
                .antMatchers("/doc.html", "/favicon.ico", "/webjars/**", "/v3/api-docs/**").permitAll()
                // 除了上面的请求以外所有的请求全部需要认证
                .anyRequest().authenticated()
                // 指定认证错误处理器
                .and().exceptionHandling().authenticationEntryPoint(new MyEntryPoint());

        // 将认证过滤器添加到Spring Security 的过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    // region 配置忽略路径
    // 配置了 permitAll() 后虽然跳过了拦截器能正常访问,但是还是会走自定义的JwtAuthenticationTokenFilter过滤器,
    // 可以添加一下方法处理,并且需要修改 JwtAuthenticationTokenFilter 已方法的方式引入到 http.addFilterBefore(new JwtAuthenticationTokenFilter(p1,p2), UsernamePasswordAuthenticationFilter.class);中
    // JwtAuthenticationTokenFilter不能注入到spring中,
    // 但是没必要处理
    // @Override
    // public void configure(WebSecurity webSecurity) {
    //     webSecurity.ignoring()
    //             .antMatchers("/doc.html", "/favicon.ico", "/webjars/**", "/v3/api-docs/**");
    // }
    // endregion

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
