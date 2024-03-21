package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.entity.Role;
import com.cn.zooey.service.RoleService;
import com.cn.zooey.vo.RoleListVO;
import com.cn.zooey.vo.RoleMenuVO;
import com.cn.zooey.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Tag(name = "角色模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/role")
public class RoleController {

    private final RoleService roleService;


    @Operation(summary = "角色列表-分页")
    @PostMapping("/pageRoleList")
    public ResResult<ResPage<Role>> pageRoleList(@Validated @RequestBody RoleListVO roleListVO) {

        return roleService.pageRoleList(roleListVO);
    }


    @Operation(summary = "新增角色")
    @PutMapping("/addRole")
    public ResResult<?> addRole(@Validated({Default.class, AddAction.class}) @RequestBody RoleVO roleVO) {

        return roleService.addRole(roleVO);
    }


    @Operation(summary = "修改角色")
    @PostMapping("/updateRole")
    public ResResult<?> updateRole(@Validated({Default.class, UpdateAction.class}) @RequestBody RoleVO roleVO) {

        return roleService.updateRole(roleVO);
    }


    @Operation(summary = "角色绑定菜单")
    @PostMapping("/bindMenu")
    public ResResult<?> bindMenu(@Validated @RequestBody RoleMenuVO roleMenuVO) {

        return roleService.bindMenu(roleMenuVO);
    }


}
