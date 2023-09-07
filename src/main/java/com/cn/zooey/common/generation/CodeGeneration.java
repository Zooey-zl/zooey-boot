package com.cn.zooey.common.generation;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.cn.zooey.common.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cn.zooey.common.generation.GenerationConsts.*;


/**
 * @Author Fengzl
 * @Date 2023/3/1 22:43
 * @Desc 代码自动生成工具类
 **/
@Slf4j
public class CodeGeneration {
    public static void main(String[] args) {
        log.info("[CodeGeneration] - 代码自动生成, 开始 ... ");
        List<String> tables = new ArrayList<>();
        tables.add("t_user");
        log.info("[CodeGeneration] - 全部表信息 -> {}", JSONUtil.toJsonStr(tables));


        /*数据库配置Builder*/
        DataSourceConfig.Builder dataSourceConfigbuilder =
                new DataSourceConfig.Builder(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
        /*项目路径 io.github.fzl.zooey*/
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/main/java";

        FastAutoGenerator.create(dataSourceConfigbuilder)
                // 全局配置(GlobalConfig)
                .globalConfig(builder -> builder.author(GLOBAL_AUTHOR)
                        // 指定输出目录
                        .outputDir(filePath)
                        // 禁止打开输出目录
                        .disableOpenDir()
                        // 开启 swagger 模式
                        .enableSwagger()
                        // 时间策略
                        .dateType(DateType.TIME_PACK)
                        // 注释日期
                        .commentDate(GLOBAL_COMMENT_DATE))
                // 包配置(PackageConfig)
                .packageConfig(builder -> builder
                        // 父包名
                        .parent(PACKAGE_PARENT)
                        // Entity 包名
                        .entity(PACKAGE_ENTITY)
                        // Service 包名
                        .service(PACKAGE_SERVICE)
                        // Service Impl 包名
                        .serviceImpl(PACKAGE_SERVICE_IMPL)
                        // Mapper 包名
                        .mapper(PACKAGE_MAPPER)
                        // Mapper XML 包名
                        .xml(PACKAGE_MAPPER_XML)
                        // Controller 包名
                        .controller(PACKAGE_CONTROLLER)
                        // 路径配置信息
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")) //路径配置信息,就是配置各个文件模板的路径信息,这里以mapper.xml为例
                        .build())
                // 配置模板引擎 freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // 模板配置(TemplateConfig)
                .templateConfig(builder -> {
                    // 实体类使用我们自定义模板
                    builder.entity("templates/myentity.java");
                })
                // 策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    // 增加表匹配(内存过滤)
                    builder.addInclude(tables)
                            // 增加过滤表前缀
                            .addTablePrefix("t_")
                            // 实体策略配置
                            .entityBuilder()
                            // 开启链式模型
                            .enableChainModel()
                            // 设置父类
                            .superClass(BaseEntity.class)
                            .addSuperEntityColumns("id", "create_time", "update_time")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            // 禁用生成 serialVersionUID
                            .disableSerialVersionUID()
                            .logicDeleteColumnName(DELETE_COLUMN_NAME)
                            .logicDeletePropertyName(DELETE_PROPERTY_NAME)
                            // 开启 lombok 模型
                            .enableLombok()
                            // 开启 Boolean 类型字段移除 is 前缀
                            .enableRemoveIsPrefix()
                            // 开启生成实体时生成字段注解
                            .enableTableFieldAnnotation()
                            // 数据库表映射到实体的命名策略 默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                            .naming(NamingStrategy.underline_to_camel)
                            // 数据库表字段映射到实体的命名策略 默认为 null，未指定按照 naming 执行
                            .columnNaming(NamingStrategy.underline_to_camel)
                            // 	全局主键类型
                            .idType(IdType.AUTO)
                            // 	格式化文件名称
                            .formatFileName("%s")
                            .build()
                            // mapper 策略配置
                            .mapperBuilder()//mapper文件策略
                            //.enableMapperAnnotation()//开启mapper注解
                            .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                            .enableBaseColumnList()//启用xml文件中的BaseColumnList
                            //.cache(缓存类.class)设置缓存实现类
                            .formatMapperFileName("%sMapper")//格式化Dao类名称
                            .formatXmlFileName("%sMapper")//格式化xml文件名称
                            .build()
                            // service 策略配置
                            .serviceBuilder()//service文件策略
                            .formatServiceFileName("%sService")//格式化 service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl")//格式化 service 接口文件名称
                            .build()
                            // controller 策略配置
                            .controllerBuilder()//控制层策略
                            //.enableHyphenStyle()开启驼峰转连字符，默认：false
                            .enableRestStyle()//开启生成@RestController
                            .formatFileName("%sController");//格式化文件名称
                })
                .execute();

    }
}
