package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.UserDepartment;
import com.cn.zooey.mapper.UserDepartmentMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 11:09
 * @desc
 **/
@Component
public class UserDepartmentRepository extends CrudRepository<UserDepartmentMapper, UserDepartment> {
}
