package com.cn.zooey.convert;


import com.cn.zooey.entity.Department;
import com.cn.zooey.vo.DepartmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentConvert {

    DepartmentConvert INSTANCE = Mappers.getMapper(DepartmentConvert.class);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    Department toDepartment(DepartmentVO departmentVO);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    void updateDepartment(DepartmentVO departmentVO, @MappingTarget Department department);

}
