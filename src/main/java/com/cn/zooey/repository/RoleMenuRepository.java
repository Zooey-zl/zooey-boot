package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.RoleMenu;
import com.cn.zooey.mapper.RoleMenuMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Fengzl
 * @date 2024/12/2 11:04
 * @desc
 **/
@Component
public class RoleMenuRepository extends CrudRepository<RoleMenuMapper, RoleMenu> {

    /**
     * 根据角色 id 获取数据,包含已删除
     * @param roleId
     * @return
     */
    public List<RoleMenu> getByRoleId(Long roleId){
        return baseMapper.getByRoleId(roleId);
    }

    /**
     * 恢复删除状态
     * @param id
     */
    public void recoverDeletedById(Long id){
        baseMapper.recoverDeletedById(id);
    }
}
