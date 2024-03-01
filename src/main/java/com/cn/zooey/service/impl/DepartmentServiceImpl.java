package com.cn.zooey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.convert.DepartmentConvert;
import com.cn.zooey.dto.DepartmentTreeDTO;
import com.cn.zooey.entity.Department;
import com.cn.zooey.mapper.DepartmentMapper;
import com.cn.zooey.service.DepartmentService;
import com.cn.zooey.vo.DepartmentListVO;
import com.cn.zooey.vo.DepartmentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {


    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public ResResult<ResPage<DepartmentTreeDTO>> pageDepartmentList(DepartmentListVO departmentListVO) {
        // 查询全部
        List<DepartmentTreeDTO> departments = departmentMapper.getAllDepartment();
        // 如果有查询条件,过滤得到有关的部门
        Set<Long> exitsDepartmentIds = new HashSet<>();
        if (StringUtils.isNotBlank(departmentListVO.getDepartmentName())) {
            List<DepartmentTreeDTO> exitsDepartments = departments.stream()
                    .filter(p -> Objects.equals(p.getDepartmentName(), departmentListVO.getDepartmentName()))
                    .collect(Collectors.toList());
            exitsDepartmentIds = exitsDepartments.stream().map(DepartmentTreeDTO::getId).collect(Collectors.toSet());
        }

        //递归生成部门树型结构数据


        return null;
    }



    private static void generateTreeData(List<DepartmentTreeDTO> res, List<DepartmentTreeDTO> departments, Long parentId, Set<Long> exitsDepartmentIds){


    }

    @Override
    public ResResult<?> addDepartment(DepartmentVO departmentVO) {
        // 校验部门名称唯一性
        checkDepartmentNameUnique(departmentVO);

        Department department = DepartmentConvert.INSTANCE.toDepartment(departmentVO);

        this.save(department);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateDepartment(DepartmentVO departmentVO) {

        Department department = this.getById(departmentVO.getId());
        if (Objects.isNull(department)) {
            throw new SaasException("部门信息不存在或已删除");
        }
        // 有修改名称,校验部门名称唯一性
        if (!Objects.equals(department.getDepartmentName(), departmentVO.getDepartmentName())) {
            checkDepartmentNameUnique(departmentVO);
        }

        DepartmentConvert.INSTANCE.updateDepartment(departmentVO, department);

        this.updateById(department);

        return ResResult.ok();
    }


    /**
     * 校验部门名称的唯一性
     * 同级别的部门, 名称不能重复
     * @param departmentVO
     */
    private void checkDepartmentNameUnique(DepartmentVO departmentVO) {
        // 同一父部门下不能有重复名称
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getParentId, departmentVO.getParentId())
                .eq(Department::getDepartmentName, departmentVO.getDepartmentName());
        Department one = this.getOne(queryWrapper);
        if (Objects.nonNull(one)) {
            throw new SaasException("同级别部门下名称不能重复");
        }
    }
}
