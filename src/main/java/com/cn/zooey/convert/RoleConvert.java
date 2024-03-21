package com.cn.zooey.convert;

import com.cn.zooey.entity.Role;
import com.cn.zooey.vo.RoleVO;
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
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    Role toRole(RoleVO roleVO);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    void updateRole(RoleVO roleVO, @MappingTarget Role role);
}
