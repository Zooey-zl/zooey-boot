package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.entity.User;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.UserListVO;
import com.cn.zooey.vo.UserRoleVO;
import com.cn.zooey.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */
@Slf4j
@Tag(name = "用户模块")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/user")
public class UserController {

    private final UserService userService;


    @Operation(summary = "用户列表")
    @PostMapping("/pageUserList")
    public ResResult<ResPage<User>> pageUserList(@RequestBody UserListVO userListVO) {

        return userService.pageUserList(userListVO);
    }


    @Operation(summary = "新增用户")
    @PutMapping("/addUser")
    public ResResult<?> addUser(@Validated({Default.class, AddAction.class}) @RequestBody UserVO userVO) {

        return userService.addUser(userVO);

    }

    @Operation(summary = "修改用户")
    @PostMapping("/updateUser")
    public ResResult<?> updateUser(@Validated({Default.class, UpdateAction.class}) @RequestBody UserVO userVO) {

        return userService.updateUser(userVO);
    }

    @Operation(summary = "删除用户", description = "不可逆")
    @DeleteMapping("/removeUser")
    public ResResult<?> removeUser(@NotNull Long id) {

        return userService.removeUser(id);
    }

    @Operation(summary = "禁用,启用用户")
    @PostMapping("/endisableUser")
    public ResResult<?> endisableUser(@NotNull Long id, @Min(value = 1) @Max(value = 2) Integer state) {

        return userService.endisableUser(id, state);
    }

    @Operation(summary = "绑定角色")
    @PostMapping("/bindRole")
    public ResResult<?> bindRole(@Validated @RequestBody UserRoleVO userRoleVO) {

        return userService.bindRole(userRoleVO);
    }

}
