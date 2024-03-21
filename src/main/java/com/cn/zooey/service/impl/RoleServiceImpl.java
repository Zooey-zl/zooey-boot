package com.cn.zooey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.util.PageUtil;
import com.cn.zooey.convert.RoleConvert;
import com.cn.zooey.entity.Role;
import com.cn.zooey.entity.RoleMenu;
import com.cn.zooey.mapper.RoleMapper;
import com.cn.zooey.mapper.RoleMenuMapper;
import com.cn.zooey.service.RoleService;
import com.cn.zooey.vo.RoleListVO;
import com.cn.zooey.vo.RoleMenuVO;
import com.cn.zooey.vo.RoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public ResResult<ResPage<Role>> pageRoleList(RoleListVO roleListVO) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();

        if (Objects.nonNull(roleListVO.getRoleType()) && roleListVO.getRoleType() > 0) {
            queryWrapper.eq(Role::getRoleType, roleListVO.getRoleType());
        }
        if (StringUtils.isNotBlank(roleListVO.getRoleName())) {
            queryWrapper.like(Role::getRoleName, roleListVO.getRoleName());
        }

        IPage<Role> iPage = PageUtil.toIPage(roleListVO.getPageable());
        IPage<Role> roleIPage = super.page(iPage, queryWrapper);

        return ResResult.ok(new ResPage<>(roleIPage));
    }

    @Override
    public ResResult<?> addRole(RoleVO roleVO) {
        Role role = RoleConvert.INSTANCE.toRole(roleVO);

        super.save(role);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateRole(RoleVO roleVO) {
        Role role = super.getById(roleVO.getId());
        if (Objects.isNull(role)) {
            throw new SaasException("角色不存在");
        }

        RoleConvert.INSTANCE.updateRole(roleVO, role);

        super.updateById(role);

        return ResResult.ok();
    }

    /**
     * 保存角色的关联菜单
     * @param roleMenuVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResResult<?> bindMenu(RoleMenuVO roleMenuVO) {
        Role role = super.getById(roleMenuVO.getRoleId());
        if (Objects.isNull(role)) {
            throw new SaasException("角色不存在");
        }
        // 获取角色现有的菜单
        List<RoleMenu> roleMenuList = roleMenuMapper.getByRoleId(roleMenuVO.getRoleId());
        // 转成 map<menuId,RoleMenu>
        Map<Long, RoleMenu> menuMap = roleMenuList.stream().collect(Collectors.toMap(RoleMenu::getMenuId, o -> o));

        // 对比新的菜单,都有的不做修改,新的添加,原有的删除
        List<Long> newMenuList = roleMenuVO.getMenuIds();
        // 转成 Map 待更新
        Map<Long, RoleMenu> newMenuMap = new HashMap<>();

        for (Long menuId : newMenuList) {
            // 存在并且删除状态为
            if (menuMap.containsKey(menuId)) {
                RoleMenu roleMenu = menuMap.get(menuId);
                if (roleMenu.isDeleted()) {
                    roleMenu.setDeleted(false);
                    newMenuMap.put(menuId, roleMenu);
                }
                menuMap.remove(menuId);
            } else {
                RoleMenu roleMenu = new RoleMenu(roleMenuVO.getRoleId(), menuId);
                newMenuMap.put(menuId, roleMenu);
            }
        }

        // 更新 DB (加锁)
        newMenuMap.values().forEach(p -> {
            if (Objects.isNull(p.getId())) {
                roleMenuMapper.insert(p);
            } else {
                roleMenuMapper.recoverDeletedById(p.getId());
            }
        });
        menuMap.values().forEach(p -> roleMenuMapper.deleteById(p.getId()));

        return ResResult.ok();
    }
}
