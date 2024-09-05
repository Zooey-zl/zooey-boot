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
public class OperBtnVO {

    @NotNull(groups = UpdateAction.class)
    @Positive(groups = UpdateAction.class)
    @Null(groups = AddAction.class)
    @Schema(description = "主键")
    private Long id;

    @NotBlank
    @Schema(description = "操作按钮名称")
    private String btnName;

    @NotBlank
    @Schema(description = "操作按钮唯一key")
    private String btnKey;

    @NotBlank
    @Schema(description = "操作按钮绑定的接口路径")
    private String path;


}
