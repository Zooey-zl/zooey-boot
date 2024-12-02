package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.RoleBtn;
import com.cn.zooey.mapper.RoleBtnMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 11:01
 * @desc
 **/
@Component
public class RoleBtnRepository extends CrudRepository<RoleBtnMapper, RoleBtn> {
}
