package com.cn.zooey.vo;

import com.cn.zooey.common.base.vo.JsonPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @Author Fengzl
 * @Date 2023/3/3 10:52
 * @Desc
 **/
@Data
public class UserListVO {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String mobile;

    @Min(value = 0)
    @Max(value = 2)
    @Schema(description = "状态: 1-启用, 2-禁用(不传或 0为全部)")
    private Integer state;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Valid
    @Schema(description = "分页参数")
    private JsonPage pageable;

}
