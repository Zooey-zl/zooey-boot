package com.cn.zooey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.zooey.common.base.TreeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_department")
@Schema(name = "Department对象", description = "部门表")
public class Department extends TreeEntity {

    @Schema(description = "部门名称")
    @TableField("department_name")
    private String departmentName;

    @Schema(description = "部门领导")
    @TableField("department_leader_uid")
    private Long departmentLeaderUid;

    @Schema(description = "父部门id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "状态,1-正常,2-禁用")
    @TableField("state")
    private Byte state;


}




