package com.cn.zooey.common.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Fengzl
 * @date 2023/10/17 14:39
 * @desc
 **/
@Data
public class JsonPage {

    @NotNull()
    @Schema(description = "页数")
    Integer page;
    @NotNull()
    @Schema(description = "条数")
    Integer size;
}
