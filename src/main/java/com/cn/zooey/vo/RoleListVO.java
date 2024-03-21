package com.cn.zooey.vo;

import com.cn.zooey.common.base.vo.JsonPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Fengzl
 * @date 2024/3/21 13:13
 * @desc
 **/
@Data
public class RoleListVO {

    @Max(value = 2)
    @Min(value = 0)
    @Schema(description = "角色类型:1-用户角色, 2-部门角色")
    private Integer roleType;

    @Schema(description = "角色名称")
    private String roleName;

    @Valid
    @Schema(description = "分页参数")
    private JsonPage pageable;
}
