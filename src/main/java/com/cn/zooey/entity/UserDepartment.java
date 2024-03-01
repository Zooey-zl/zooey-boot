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
 * 用户部门关联表
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_user_department")
@Schema(name = "UserDepartment对象", description = "用户部门关联表")
public class UserDepartment extends BaseEntity {

    @Schema(description = "用户ID")
    @TableField("uid")
    private Long uid;

    @Schema(description = "部门ID")
    @TableField("department_id")
    private Long departmentId;


}




