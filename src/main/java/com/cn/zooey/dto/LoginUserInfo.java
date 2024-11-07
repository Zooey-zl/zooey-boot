package com.cn.zooey.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Fengzl
 * @date 2024/11/7 15:53
 * @desc
 **/
@Data
public class LoginUserInfo {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名")
    private String userName;
}
