package com.cn.zooey.mapper;

import com.cn.zooey.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色 id 获取数据,包含已删除
     * @param roleId
     * @return
     */
    List<RoleMenu> getByRoleId(Long roleId);

    /**
     * 恢复删除状态
     * @param id
     */
    void recoverDeletedById(Long id);
}
