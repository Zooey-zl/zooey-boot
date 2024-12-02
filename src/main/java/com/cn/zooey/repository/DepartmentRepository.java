package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.Department;
import com.cn.zooey.mapper.DepartmentMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Fengzl
 * @date 2024/12/2 10:31
 * @desc
 **/
@Component
public class DepartmentRepository extends CrudRepository<DepartmentMapper, Department> {

    public List<Department> getAllDepartment(){
        return baseMapper.getAllDepartment();
    }

}
