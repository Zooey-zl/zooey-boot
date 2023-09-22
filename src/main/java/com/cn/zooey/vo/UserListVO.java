package com.cn.zooey.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author Fengzl
 * @Date 2023/3/3 10:52
 * @Desc
 **/
@Data
public class UserListVO {

    @Schema(description = "用户Id")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "性别:0-不详,1-男,2-女")
    private Integer gender;

    @Schema(description = "出生年月")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String headUrl;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态: 1-启用, 2-禁用")
    private Integer state;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
