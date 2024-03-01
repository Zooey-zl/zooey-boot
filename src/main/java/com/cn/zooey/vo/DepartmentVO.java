package com.cn.zooey.vo;

import com.cn.zooey.common.constraints.group.UpdateAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Fengzl
 * @date 2023/10/17 18:07
 * @desc
 **/
@Data
public class DepartmentVO {

    @NotNull(groups = UpdateAction.class)
    private Long id;
    @NotBlank
    @Schema(description = "部门名称")
    private String departmentName;
    @NotNull
    @Schema(description = "部门领导用户ID")
    private Long departmentLeaderUid;
    @NotNull
    @Schema(description = "父部门ID")
    private Long parentId;
}
