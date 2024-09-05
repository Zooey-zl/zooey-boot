package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.entity.OperBtn;
import com.cn.zooey.service.OperBtnService;
import com.cn.zooey.vo.OperBtnListVO;
import com.cn.zooey.vo.OperBtnVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * <p>
 * 操作按钮表 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Tag(name = "权限点模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/operBtn")
public class OperBtnController {
    
    private final OperBtnService operBtnService;

    //权限点列表
    @Operation(summary = "权限点列表-分页")
    @GetMapping("/listOperBtn")
    public ResResult<ResPage<OperBtn>> pageOperBtnList(@Validated @RequestBody OperBtnListVO operBtnListVO) {

        return operBtnService.listOperBtn(operBtnListVO);

    }

    @Operation(summary = "新增权限点")
    @PutMapping("/addOperBtn")
    public ResResult<?> addOperBtn(@RequestBody @Validated({Default.class, AddAction.class}) OperBtnVO OperBtnVO) {

        return operBtnService.addOperBtn(OperBtnVO);
    }

    @Operation(summary = "修改权限点")
    @PostMapping("/updateOperBtn")
    public ResResult<?> updateOperBtn(@RequestBody @Validated({Default.class, UpdateAction.class}) OperBtnVO OperBtnVO) {

        return operBtnService.updateOperBtn(OperBtnVO);
    }

    @Operation(summary = "删除权限点", description = "不可逆")
    @DeleteMapping("/removeOperBtn")
    public ResResult<?> removeOperBtn(@NotNull @Positive Long id) {

        return operBtnService.removeOperBtn(id);
    }

    @Operation(summary = "禁用,启用权限点")
    @PostMapping("/endisableOperBtn")
    public ResResult<?> endisableOperBtn(@NotNull Long id, @Min(value = 1) @Max(value = 2) Byte state) {

        return operBtnService.endisableOperBtn(id, state);
    }



}
