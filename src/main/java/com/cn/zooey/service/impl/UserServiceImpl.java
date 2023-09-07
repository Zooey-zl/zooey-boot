package com.cn.zooey.service.impl;

import com.cn.zooey.entity.User;
import com.cn.zooey.mapper.UserMapper;
import com.cn.zooey.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
