package com.cn.zooey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.dto.LoginUser;
import com.cn.zooey.entity.User;
import com.cn.zooey.vo.LoginVO;
import com.cn.zooey.vo.UserListVO;
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
     * 用户列表
     * @param userListVO
     * @return
     */
    ResResult<ResPage<User>> pageUserList(UserListVO userListVO);
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

    /**
     * 账号密码登录
     * @param loginVO
     * @return
     */
    ResResult<LoginUser> login(LoginVO loginVO);

    /**
     * 退出登录
     * @return
     */
    ResResult<?> logout();
}
