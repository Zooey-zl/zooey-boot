package com.cn.zooey.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author Fengzl
 * @Date 2023/3/2 14:00
 * @Desc 表实体基类
 **/
@Data
public class BaseEntity {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //用来接收字符串类型的参数封装成LocalDate类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")        //将date 类型数据转成字符串响应出去
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)     //序列化
    private LocalDateTime createTime;
    /**
     * 修改时间
     * update = "now()" 因为 mybatis-plus 更新填充策略问题,无法更新,所以在此处设置新值
     */
    @Schema(description = "修改时间")
    @TableField(value = "update_time", update = "now()")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //用来接收字符串类型的参数封装成LocalDate类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")        //将date 类型数据转成字符串响应出去
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)     //序列化
    private LocalDateTime updateTime;

    /**
     * 逻辑删除 false-正常,true-删除
     */
    @Schema(description = "逻辑删除")
    @TableLogic
    private boolean deleted;
}
