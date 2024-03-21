package com.cn.zooey.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author Fengzl
 * @date 2024/3/21 14:00
 * @desc 角色菜单关联
 **/
@Data
public class RoleMenuVO {

    @NotNull
    @Positive
    @Schema(description = "角色 id")
    private Long roleId;

    @NotNull
    @Schema(description = "菜单 id集合")
    private List<Long> menuIds;
}
