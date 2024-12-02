package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.UserRole;
import com.cn.zooey.mapper.UserRoleMapper;

import java.util.List;

/**
 * @author Fengzl
 * @date 2024/12/2 11:13
 * @desc
 **/
public class UserRoleRepository extends CrudRepository<UserRoleMapper, UserRole> {

    /**
     * 根据用户 id 获取,包括删除
     * @param userId
     * @return
     */
    public List<UserRole> getByUserId(Long userId){
        return baseMapper.getByUserId(userId);
    }


    /**
     * 恢复删除状态
     * @param id
     */
    public void recoverDeletedById(Long id){
        baseMapper.recoverDeletedById(id);
    }
}
