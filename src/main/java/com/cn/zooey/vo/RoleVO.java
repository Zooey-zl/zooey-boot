package com.cn.zooey.vo;

import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

/**
 * @author Fengzl
 * @date 2024/3/21 13:20
 * @desc
 **/
@Data
public class RoleVO {

    @NotNull(groups = UpdateAction.class)
    @Positive(groups = UpdateAction.class)
    @Null(groups = AddAction.class)
    private Long id;

    @NotNull
    @Schema(description = "角色类型:1-用户角色,2-部门角色")
    private Integer roleType;
    @NotBlank
    @Schema(description = "角色名称")
    private String RoleName;
}
