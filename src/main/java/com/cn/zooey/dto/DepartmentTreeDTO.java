package com.cn.zooey.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author Fengzl
 * @date 2023/11/7 14:52
 * @desc
 **/
@Data
public class DepartmentTreeDTO {

    @Schema(description = "部门id")
    private Long id;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "部门领导")
    private Long departmentLeaderUid;

    @Schema(description = "父部门id")
    private Long parentId;

    @Schema(description = "状态,1-正常,2-禁用")
    private Byte state;

    @Schema(description = "子部门数据")
    private List<DepartmentTreeDTO> children;
}
