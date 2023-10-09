package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.dto.LoginUser;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * @author Fengzl
 * @date 2023/10/7 11:21
 * @desc
 **/
@Tag(name = "登录模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/login")
public class LoginController {

    private final UserService userService;


    @Operation(summary = "登录")
    @PostMapping("/verifier")
    public ResResult<LoginUser> verifier(@Valid @RequestBody LoginVO loginVO) {

        return userService.login(loginVO);

    }


    @Operation(summary = "登出")
    @PostMapping("/logout")
    public ResResult<?> logout() {

        return userService.logout();

    }
}
