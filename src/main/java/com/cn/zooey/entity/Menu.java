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
 * 菜单表
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_menu")
@Schema(name = "Menu对象", description = "菜单表")
public class Menu extends TreeEntity {

    @Schema(description = "菜单名称")
    @TableField("name")
    private String name;

    @Schema(description = "路径")
    @TableField("path")
    private String path;


    @Schema(description = "图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "是否是外链,0-否,1-是")
    @TableField("target_link")
    private Boolean targetLink;

    @Schema(description = "权重,用于排序")
    @TableField("weight")
    private Integer weight;

    @Schema(description = "状态,1-正常,2-禁用")
    @TableField("state")
    private Byte state;


}




