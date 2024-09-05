package com.cn.zooey.convert;

import com.cn.zooey.entity.OperBtn;
import com.cn.zooey.vo.OperBtnVO;
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
public interface OperBtnConvert {
    OperBtnConvert INSTANCE = Mappers.getMapper(OperBtnConvert.class);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    OperBtn toOperBtn(OperBtnVO operBtnVO);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    void updateOperBtn(OperBtnVO operBtnVO, @MappingTarget OperBtn operBtn);
}
