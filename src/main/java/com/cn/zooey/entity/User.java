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
 *
 * </p>
 *
 * @author Fengzl
 * @since 2023-06-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_user")
@Schema(title = "User对象", description = "用户")
public class User extends BaseEntity {

    @Schema(description = "姓名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "年龄")
    @TableField("age")
    private Integer age;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;


}




