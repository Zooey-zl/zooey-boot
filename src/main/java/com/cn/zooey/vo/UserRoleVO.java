package com.cn.zooey.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author Fengzl
 * @date 2024/3/21 14:00
 * @desc 用户角色关联
 **/
@Data
public class UserRoleVO {

    @NotNull
    @Positive
    @Schema(description = "用户 id")
    private Long userId;

    @NotNull
    @Schema(description = "角色 id集合")
    private List<Long> roleIds;
}
