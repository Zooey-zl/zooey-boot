package com.cn.zooey.common.generation;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import java.util.Map;

import static com.cn.zooey.common.generation.GenerationConsts.*;

/**
 * @author Fengzl
 * @date 2024/12/2 13:49
 * @desc
 **/
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/main/java/" + PACKAGE_PARENT.replace(".", File.separator) + File.separator;
        customFile.forEach((custom) -> {
            String fileName = String.format(filePath + PACKAGE_REPOSITORY + File.separator + entityName + "%s", custom.getFileName());
            this.outputFile(new File(fileName), objectMap, custom.getTemplatePath(), false);
        });
    }
}
