package com.cn.zooey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.entity.Department;
import com.cn.zooey.vo.DepartmentVO;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface DepartmentService extends IService<Department> {


    /**
     * 部门列表(树形列表,可分页,顶级部门作为分页计数)
     * @return
     */
    ResResult<List<Department>> pageDepartmentList();

    /**
     * 新增部门
     * @param departmentVO
     * @return
     */
    ResResult<?> addDepartment(DepartmentVO departmentVO);

    /**
     * 修改部门
     * @param departmentVO
     * @return
     */
    ResResult<?> updateDepartment(DepartmentVO departmentVO);


}
