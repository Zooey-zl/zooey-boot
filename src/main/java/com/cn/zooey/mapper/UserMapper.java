package com.cn.zooey.mapper;

import com.cn.zooey.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     */
    User getUserByMobile(@Param("mobile") String mobile);
}
