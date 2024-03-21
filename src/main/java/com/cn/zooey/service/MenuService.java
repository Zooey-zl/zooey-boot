package com.cn.zooey.service;

import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface MenuService extends IService<Menu> {

    /**
     * 菜单列表-树型结构
     * @return
     */
    ResResult<List<Menu>> listMenu();

    /**
     * 新增菜单
     * @param menuVO
     * @return
     */
    ResResult<?> addMenu(MenuVO menuVO);

    /**
     * 修改菜单
     * @param menuVO
     * @return
     */
    ResResult<?> updateMenu(MenuVO menuVO);

    /**
     * 删除菜单 不可逆
     * @param id
     * @return
     */
    ResResult<?> removeMenu(Long id);

    /**
     * 启用,禁用菜单
     * @param id
     * @param state
     * @return
     */
    ResResult<?> endisableMenu(Long id, Byte state);
}
