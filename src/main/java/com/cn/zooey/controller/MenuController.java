package com.cn.zooey.controller;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.entity.Menu;
import com.cn.zooey.service.MenuService;
import com.cn.zooey.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

import static com.cn.zooey.constant.GlobalConstant.API_PREFIX;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Tag(name = "菜单模块")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/menu")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "菜单列表-树型")
    @GetMapping("/listMenu")
    public ResResult<List<Menu>> listMenu() {

        return menuService.listMenu();
    }

    @Operation(summary = "新增菜单")
    @PutMapping("/addMenu")
    public ResResult<?> addMenu(@RequestBody @Validated({Default.class, AddAction.class}) MenuVO menuVO) {

        return menuService.addMenu(menuVO);
    }

    @Operation(summary = "修改菜单")
    @PostMapping("/updateMenu")
    public ResResult<?> updateMenu(@RequestBody @Validated({Default.class, UpdateAction.class}) MenuVO menuVO) {

        return menuService.updateMenu(menuVO);
    }

    @Operation(summary = "删除菜单", description = "不可逆")
    @DeleteMapping("/removeMenu")
    public ResResult<?> removeMenu(@NotNull Long id) {

        return menuService.removeMenu(id);
    }

    @Operation(summary = "禁用,启用菜单")
    @PostMapping("/endisableMenu")
    public ResResult<?> endisableMenu(@NotNull Long id, @Min(value = 1) @Max(value = 2) Byte state) {

        return menuService.endisableMenu(id, state);
    }

}
