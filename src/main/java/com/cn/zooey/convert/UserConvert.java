package com.cn.zooey.convert;

import com.cn.zooey.entity.User;
import com.cn.zooey.vo.UserVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE =  Mappers.getMapper(UserConvert.class);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "state", ignore = true)
    })
    User toUser(UserVO userVO);


    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "state", ignore = true),
    })
    void updateUser(UserVO userVO, @MappingTarget User user);
}
