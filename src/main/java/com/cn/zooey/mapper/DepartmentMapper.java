package com.cn.zooey.mapper;

import com.cn.zooey.dto.DepartmentTreeDTO;
import com.cn.zooey.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    List<DepartmentTreeDTO> getAllDepartment();

}
