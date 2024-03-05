package com.cn.zooey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.cache.DataCache;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.constant.GlobalConstant;
import com.cn.zooey.convert.UserConvert;
import com.cn.zooey.dto.LoginUser;
import com.cn.zooey.entity.User;
import com.cn.zooey.mapper.UserMapper;
import com.cn.zooey.service.UserService;
import com.cn.zooey.util.JWTUtil;
import com.cn.zooey.vo.LoginVO;
import com.cn.zooey.vo.UserListVO;
import com.cn.zooey.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private DataCache dataCache;

    @Override
    public User getUserByMobile(String mobile) {

        return userMapper.getUserByMobile(mobile);
    }


    @Override
    public ResResult<ResPage<User>> pageUserList(UserListVO userListVO) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(userListVO.getUserName())) {
            queryWrapper.like(User::getUserName, userListVO.getUserName());
        }
        if (StringUtils.isNotBlank(userListVO.getMobile())) {
            queryWrapper.like(User::getMobile, userListVO.getMobile());
        }
        if (Objects.nonNull(userListVO.getStartTime())) {
            queryWrapper.ge(User::getCreateTime, userListVO.getStartTime());
        }
        if (Objects.nonNull(userListVO.getEndTime())) {
            queryWrapper.le(User::getCreateTime, userListVO.getEndTime());
        }
        if (Objects.nonNull(userListVO.getState()) && userListVO.getState() > 0) {
            queryWrapper.eq(User::getState, userListVO.getState());
        }

        IPage<User> iPage = Page.of(userListVO.getPageable().getPage(), userListVO.getPageable().getSize());
        IPage<User> userIPage = super.page(iPage, queryWrapper);

        return ResResult.ok(new ResPage<>(userIPage));
    }

    @Override
    public ResResult<?> addUser(UserVO userVO) {
        log.info("接收参数: {}", JSONObject.toJSONString(userVO));
        User userByMobile = getUserByMobile(userVO.getMobile());
        if (Objects.nonNull(userByMobile)) {
            throw new SaasException("手机号已存在");
        }

        User user = UserConvert.INSTANCE.toUser(userVO);
        log.info("转换后参数: {}", JSONObject.toJSONString(user));
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        super.save(user);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateUser(UserVO userVO) {
        log.info("接收参数: {}", JSONObject.toJSONString(userVO));
        User user = super.getById(userVO.getId());
        if (Objects.isNull(user)) {
            throw new SaasException("用户不存在");
        }
        if (!Objects.equals(user.getMobile(), userVO.getMobile())) {
            throw new SaasException("不允许修改手机号");
        }

        UserConvert.INSTANCE.updateUser(userVO, user);
        log.info("转换后参数: {}", JSONObject.toJSONString(user));

        super.updateById(user);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> removeUser(Long id) {
        User user = super.getById(id);

        Optional.ofNullable(user).filter(p -> !p.isDeleted()).orElseThrow(() -> new SaasException("用户不存在或不支持此操作"));

        super.removeById(user);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> endisableUser(Long id, Integer state) {
        User user = super.getById(id);

        Optional.ofNullable(user).filter(p -> !Objects.equals(p.getState(), state)).orElseThrow(() -> new SaasException("用户不存在或不支持此操作"));

        user.setState(state);
        super.updateById(user);

        return ResResult.ok();
    }


    @Override
    public ResResult<LoginUser> login(LoginVO loginVO) {

        // 创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVO.getUserName(), loginVO.getPassword());

        // 调用AuthenticationManager的authenticate方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new SaasException("用户名和密码错误");
        }

        // 获取用户的数据信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // JWT生成token
        String token = JWTUtil.generate(loginUser.getId().toString());

        loginUser.setToken(token);
        // 存储到redis
        dataCache.setLoginToken(GlobalConstant.GLOBAL_TOKEN_KEY, GlobalConstant.TOKEN_KEY_PREFIX + loginUser.getId(),
                JSONObject.toJSONString(loginUser));
        // 登录日志 登录信息(使用浏览器,IP,所在地,时间,用户id), 存es,存库
        // 在线人数,统计有效token


        return ResResult.ok(loginUser);
    }


    @Override
    public ResResult<?> logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 删除JWT
        SecurityContextHolder.clearContext();
        dataCache.removeLoginToken(GlobalConstant.GLOBAL_TOKEN_KEY, GlobalConstant.TOKEN_KEY_PREFIX + loginUser.getId());
        return ResResult.ok("退出成功");
    }
}
