package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.Role;
import com.cn.zooey.mapper.RoleMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 11:02
 * @desc
 **/
@Component
public class RoleRepository extends CrudRepository<RoleMapper, Role> {

}
