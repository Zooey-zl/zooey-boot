package com.cn.zooey.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fengzl
 * @date 2024/3/5 11:56
 * @desc Tree 基类
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class TreeEntity extends BaseEntity{


    @Schema(description = "父id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "子菜单")
    @TableField(exist = false)
    private List<?> children = new ArrayList<>();


}
