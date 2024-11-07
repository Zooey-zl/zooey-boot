package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.dto.LoginToken;
import com.cn.zooey.dto.LoginUserInfo;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;
import static com.cn.zooey.constant.GlobalConstant.TOKEN_HEADER;

/**
 * @author Fengzl
 * @date 2023/10/7 11:21
 * @desc
 **/
@Tag(name = "登录模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/auth")
public class LoginController {

    private final UserService userService;


    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResResult<LoginToken> login(@Valid @RequestBody LoginVO loginVO) {

        return userService.login(loginVO);

    }


    @Operation(summary = "获取登录用户信息")
    @GetMapping("/getUserInfo")
    public ResResult<LoginUserInfo> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        return userService.getUserInfo(token);

    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public ResResult<?> logout() {

        return userService.logout();

    }
}
