package com.cn.zooey.vo;

import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.common.constraints.pattern.password.Password;
import com.cn.zooey.common.constraints.pattern.phone.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @Author Fengzl
 * @Date 2023/6/25 16:12
 * @Desc
 **/
@Data
public class UserVO {

    @NotNull(groups = UpdateAction.class)
    @Positive(groups = UpdateAction.class)
    @Null(groups = AddAction.class)
    @Schema(description = "id")
    private Long id;

    @NotBlank
    @Schema(description = "用户名")
    private String userName;

    @Phone
    @Schema(description = "手机号")
    private String mobile;

    @Min(value = 1)
    @Max(value = 2)
    @Schema(description = "性别:0-不详,1-男,2-女")
    private Integer gender;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "出生年月")
    private LocalDate birthday;

    @NotBlank(groups = AddAction.class)
    @Password(groups = AddAction.class, message = "密码格式不正确")
    @Schema(description = "密码")
    private String password;

    @Email
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String headUrl;

    @NotBlank
    @Schema(description = "地址")
    private String address;
}
