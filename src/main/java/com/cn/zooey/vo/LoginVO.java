package com.cn.zooey.vo;

import com.cn.zooey.common.constraints.pattern.password.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Fengzl
 * @date 2023/10/7 11:26
 * @desc
 **/
@Data
public class LoginVO {

    @NotBlank()
    @Schema(description = "用户名")
    private String userName;

    @NotBlank
    @Password
    @Schema(description = "密码")
    private String password;
}
