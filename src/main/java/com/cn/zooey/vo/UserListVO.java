package com.cn.zooey.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @Author Fengzl
 * @Date 2023/3/3 10:52
 * @Desc
 **/
@Data
public class UserListVO {

    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "邮箱")
    @Email
    private String email;
}
