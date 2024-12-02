package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.User;
import com.cn.zooey.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 11:10
 * @desc
 **/
@Component
public class UserRepository extends CrudRepository<UserMapper, User> {


    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     */
    public User getUserByMobile(@Param("mobile") String mobile){
        return baseMapper.getUserByMobile(mobile);
    }
}
