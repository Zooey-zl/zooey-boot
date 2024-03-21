package com.cn.zooey.mapper;

import com.cn.zooey.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户角色关联表(优先级低于部门角色) Mapper 接口
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户 id 获取,包括删除
     * @param userId
     * @return
     */
    List<UserRole> getByUserId(Long userId);


    /**
     * 恢复删除状态
     * @param id
     */
    void recoverDeletedById(Long id);
}
