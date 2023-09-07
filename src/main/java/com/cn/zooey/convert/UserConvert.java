package com.cn.zooey.convert;

import com.cn.zooey.entity.User;
import com.cn.zooey.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author Fengzl
 * @Date 2023/6/25 16:49
 * @Desc
 **/
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    /**
     * 转换
     * @param userVO
     * @return
     */
    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "createTime", ignore = true)
    })
    User toUser(UserVO userVO);


    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    void updateUser(UserVO userVO, @MappingTarget User user);
}
