package com.cn.zooey.service;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */
public interface UserService extends IService<User> {


    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile);

    /**
     * 新增用户
     * @param userVO
     * @return
     */
    ResResult<?> addUser(UserVO userVO);

    /**
     * 修改用户
     * @param userVO
     * @return
     */
    ResResult<?> updateUser(UserVO userVO);

    /**
     * 删除用户
     * @param id
     * @return
     */
    ResResult<?> removeUser(Long id);

    /**
     * 禁用启用
     * @param id
     * @param state
     * @return
     */
    ResResult<?> endisableUser(Long id, Integer state);
}
