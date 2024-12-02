package com.cn.zooey.service.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.dto.LoginUser;
import com.cn.zooey.entity.User;
import com.cn.zooey.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Fengzl
 * @date 2023/10/7 10:45
 * @desc
 **/
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("[验证用户是否有权登录] -> {}", username);
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUserName, username).eq(User::getState, 1);

        User user = userRepository.getOne(queryWrapper);

        if (Objects.isNull(user)) {
            throw new SaasException("用户不存在");
        }

        return new LoginUser(user);
    }
}
