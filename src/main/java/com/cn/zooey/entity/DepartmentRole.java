package com.cn.zooey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.zooey.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门角色关联表(优先级高于用户角色)
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_department_role")
@Schema(name = "DepartmentRole对象", description = "部门角色关联表(优先级高于用户角色)")
public class DepartmentRole extends BaseEntity {

    @Schema(description = "部门ID")
    @TableField("department_id")
    private Long departmentId;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Long roleId;


}




