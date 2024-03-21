package com.cn.zooey.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.convert.DepartmentConvert;
import com.cn.zooey.entity.Department;
import com.cn.zooey.mapper.DepartmentMapper;
import com.cn.zooey.service.DepartmentService;
import com.cn.zooey.common.util.TreeUtil;
import com.cn.zooey.vo.DepartmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Slf4j
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {


    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public ResResult<List<Department>> pageDepartmentList() {
        // 查询全部
        List<Department> departments = departmentMapper.getAllDepartment();
        // 查询最高父节点
        List<Department> rootNode = TreeUtil.selectRootNodeData(departments);

        // 递归生成部门树型结构数据
        rootNode.forEach(p -> p.setChildren(TreeUtil.getChildren(departments, p.getId())));
        log.info("部门树型列表: {}", JSONObject.toJSONString(rootNode));

        return ResResult.ok(rootNode);
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
