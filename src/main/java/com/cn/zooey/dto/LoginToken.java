package com.cn.zooey.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Fengzl
 * @date 2024/11/7 15:15
 * @desc
 **/
@Data
public class LoginToken {

    @Schema(description = "token")
    private String token;

    @Schema(description = "刷新token")
    private String refreshToken;
}
