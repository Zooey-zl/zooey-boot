package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.dto.DepartmentTreeDTO;
import com.cn.zooey.service.DepartmentService;
import com.cn.zooey.vo.DepartmentListVO;
import com.cn.zooey.vo.DepartmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Tag(name = "部门模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/department")
public class DepartmentController {

    private final DepartmentService departmentService;


    @Operation(summary = "部门列表-树形")
    @PostMapping("/pageDepartmentList")
    public ResResult<ResPage<DepartmentTreeDTO>> pageDepartmentList(@RequestBody DepartmentListVO departmentListVO) {

        return departmentService.pageDepartmentList(departmentListVO);
    }

    @Operation(summary = "新增部门")
    @PutMapping("/addDepartment")
    public ResResult<?> addDepartment(@Validated() @RequestBody DepartmentVO departmentVO) {

        return departmentService.addDepartment(departmentVO);
    }

    @Operation(summary = "修改部门")
    @PostMapping("/updateDepartment")
    public ResResult<?> updateDepartment(@Validated({Default.class, UpdateAction.class}) @RequestBody DepartmentVO departmentVO) {

        return departmentService.updateDepartment(departmentVO);
    }

}
