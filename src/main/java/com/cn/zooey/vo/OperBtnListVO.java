package com.cn.zooey.vo;

import com.cn.zooey.common.base.vo.JsonPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Fengzl
 * @date 2023/10/17 18:07
 * @desc
 **/
@Data
public class OperBtnListVO {

    @Schema(description = "权限点名称")
    private String btnName;

    @Valid
    @Schema(description = "分页参数")
    private JsonPage pageable;
}
