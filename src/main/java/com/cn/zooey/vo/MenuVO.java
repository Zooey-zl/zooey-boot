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
 * @date 2024/3/21 11:32
 * @desc
 **/
@Data
public class MenuVO {

    @NotNull(groups = UpdateAction.class)
    @Positive(groups = UpdateAction.class)
    @Null(groups = AddAction.class)
    @Schema(description = "主键")
    private Long id;

    @NotBlank
    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "路径")
    private String path;

    @NotNull
    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "图标")
    private String icon;

    @NotNull
    @Schema(description = "是否是外链,0-否,1-是")
    private Boolean targetLink;

    @NotNull
    @Schema(description = "权重,用于排序")
    private Integer weight;
}
