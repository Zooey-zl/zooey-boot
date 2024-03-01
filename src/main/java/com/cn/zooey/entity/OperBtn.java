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
 * 操作按钮表
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_oper_btn")
@Schema(name = "OperBtn对象", description = "操作按钮表")
public class OperBtn extends BaseEntity {

    @Schema(description = "操作按钮名称")
    @TableField("btn_name")
    private String btnName;

    @Schema(description = "操作按钮唯一key")
    @TableField("btn_key")
    private String btnKey;

    @Schema(description = "操作按钮绑定的接口路径")
    @TableField("path")
    private String path;

    @Schema(description = "状态,1-正常,2-禁用")
    @TableField("state")
    private Byte state;


}




