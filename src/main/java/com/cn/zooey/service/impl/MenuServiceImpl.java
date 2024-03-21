package com.cn.zooey.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.convert.MenuConvert;
import com.cn.zooey.entity.Menu;
import com.cn.zooey.mapper.MenuMapper;
import com.cn.zooey.service.MenuService;
import com.cn.zooey.common.util.TreeUtil;
import com.cn.zooey.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public ResResult<List<Menu>> listMenu() {

        // 获取全部菜单, 排除删除
        List<Menu> allNode = super.list();
        // 获取根节点数据
        List<Menu> rootNode = TreeUtil.selectRootNodeData(allNode);
        // 递归生成树型结构
        rootNode.forEach(p -> p.setChildren(TreeUtil.getChildren(allNode, p.getId())));

        return ResResult.ok(rootNode);
    }

    @Override
    public ResResult<?> addMenu(MenuVO menuVO) {

        Menu menu = MenuConvert.INSTANCE.toMenu(menuVO);

        super.save(menu);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateMenu(MenuVO menuVO) {
        Menu menu = super.getById(menuVO.getId());
        if (Objects.isNull(menu)) {
            throw new SaasException("菜单不存在");
        }

        MenuConvert.INSTANCE.updateMenu(menuVO, menu);

        super.updateById(menu);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> removeMenu(Long id) {
        Menu menu = super.getById(id);

        Optional.ofNullable(menu).filter(p -> !p.isDeleted()).orElseThrow(() -> new SaasException("菜单不存在或不支持此操作"));

        super.removeById(menu);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> endisableMenu(Long id, Byte state) {
        Menu menu = super.getById(id);

        Optional.ofNullable(menu).filter(p -> !Objects.equals(p.getState(), state)).orElseThrow(() -> new SaasException("菜单不存在或不支持此操作"));

        menu.setState(state);
        super.updateById(menu);

        return ResResult.ok();
    }
}
