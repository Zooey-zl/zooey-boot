package com.cn.zooey.convert;

import com.cn.zooey.entity.Menu;
import com.cn.zooey.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Fengzl
 * @date 2024/3/21 11:48
 * @desc
 **/
@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    Menu toMenu(MenuVO menuVO);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    void updateMenu(MenuVO menuVO, @MappingTarget Menu menu);
}
