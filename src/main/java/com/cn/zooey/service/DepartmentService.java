package com.cn.zooey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.dto.DepartmentTreeDTO;
import com.cn.zooey.entity.Department;
import com.cn.zooey.vo.DepartmentListVO;
import com.cn.zooey.vo.DepartmentVO;

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
     * @param departmentListVO
     * @return
     */
    ResResult<ResPage<DepartmentTreeDTO>> pageDepartmentList(DepartmentListVO departmentListVO);

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
