package com.cn.zooey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.entity.Role;
import com.cn.zooey.vo.RoleListVO;
import com.cn.zooey.vo.RoleMenuVO;
import com.cn.zooey.vo.RoleVO;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface RoleService extends IService<Role> {

    /**
     * 角色列表 - 分页
     * @param roleListVO
     * @return
     */
    ResResult<ResPage<Role>> pageRoleList(RoleListVO roleListVO);

    /**
     * 新增角色
     * @param roleVO
     * @return
     */
    ResResult<?> addRole(RoleVO roleVO);

    /**
     * 修改角色
     * @param roleVO
     * @return
     */
    ResResult<?> updateRole(RoleVO roleVO);

    /**
     * 角色绑定菜单 保存
     * @param roleMenuVO
     * @return
     */
    ResResult<?> bindMenu(RoleMenuVO roleMenuVO);
}
