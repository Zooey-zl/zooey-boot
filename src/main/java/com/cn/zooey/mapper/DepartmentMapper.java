package com.cn.zooey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.zooey.entity.Department;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取全部部门,排除删除
     * @return DepartmentTreeDTO
     */
    List<Department> getAllDepartment();


}
