package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/user")
public class UserController {

    private final UserService userService;


    // 用户列表

    // 查询用户

    @Operation(summary = "新增用户")
    @PostMapping("/addUser")
    public ResResult<?> addUser(@Validated({Default.class, AddAction.class}) @RequestBody UserVO userVO) {

        return userService.addUser(userVO);

    }

    @Operation(summary = "修改用户")
    @PostMapping("/updateUser")
    public ResResult<?> updateUser(@Validated({Default.class, UpdateAction.class}) @RequestBody UserVO userVO) {

        return userService.updateUser(userVO);
    }

    // 删除用户

}
