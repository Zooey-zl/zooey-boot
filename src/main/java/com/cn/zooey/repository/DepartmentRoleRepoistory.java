package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.DepartmentRole;
import com.cn.zooey.mapper.DepartmentRoleMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 10:45
 * @desc
 **/
@Component
public class DepartmentRoleRepoistory extends CrudRepository<DepartmentRoleMapper, DepartmentRole> {
}
