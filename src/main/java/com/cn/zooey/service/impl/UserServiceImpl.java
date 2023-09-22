package com.cn.zooey.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.convert.UserConvert;
import com.cn.zooey.entity.User;
import com.cn.zooey.mapper.UserMapper;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public User getUserByMobile(String mobile) {

        return userMapper.getUserByMobile(mobile);
    }

    @Override
    public ResResult<?> addUser(UserVO userVO) {
        log.info("接收参数: {}", JSONUtil.toJsonStr(userVO));
        User userByMobile = getUserByMobile(userVO.getMobile());
        if (Objects.nonNull(userByMobile)) {
            throw new SaasException("手机号已存在");
        }

        User user = UserConvert.INSTANCE.toUser(userVO);
        log.info("转换后参数: {}", JSONUtil.toJsonStr(user));
        // 密码加密 todo

        super.save(user);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateUser(UserVO userVO) {
        log.info("接收参数: {}", JSONUtil.toJsonStr(userVO));
        User user = super.getById(userVO.getId());
        if (Objects.isNull(user)) {
            throw new SaasException("用户不存在");
        }
        if (!Objects.equals(user.getMobile(), userVO.getMobile())) {
            throw new SaasException("不允许修改手机号");
        }

        UserConvert.INSTANCE.updateUser(userVO, user);
        log.info("转换后参数: {}", JSONUtil.toJsonStr(user));

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
}
