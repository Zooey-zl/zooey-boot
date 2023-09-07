package com.cn.zooey.vo;

import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

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
    @Schema(description = "姓名")
    private String userName;
    @NotNull
    @Min(value = 1)
    @Max(value = 200)
    @Schema(description = "年龄")
    private Integer age;
    @Email
    @NotBlank
    @Schema(description = "邮箱")
    private String email;
}
