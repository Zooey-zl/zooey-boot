package com.cn.zooey.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.zooey.common.base.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Fengzl
 * @since 2023-09-07
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "User对象", description = "用户表")
public class User extends BaseEntity {

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "手机号")
    @TableField("mobile")
    private String mobile;

    @Schema(description = "性别:0-不详,1-男,2-女")
    @TableField("gender")
    private Integer gender;

    @Schema(description = "出生年月")
    @TableField("birthday")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "头像")
    @TableField("head_url")
    private String headUrl;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "状态: 1-启用, 2-禁用")
    @TableField("state")
    private Integer state;


}




