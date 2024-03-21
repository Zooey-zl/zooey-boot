package com.cn.zooey.common.util;

import com.cn.zooey.common.base.TreeEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Fengzl
 * @date 2024/3/21 11:10
 * @desc 生成树型结构工具类
 **/
public class TreeUtil {


    /**
     * 根据父节点ID递归填充子节点
     * <br/>泛型 T 需要继承 TreeEntity
     * @param allNode
     * @param parentId
     * @return
     */
    public static <T extends TreeEntity> List<T> getChildren(List<T> allNode, Long parentId) {
        // 下一级子节点
        List<T> departments = allNode.stream().filter(item -> Objects.equals(item.getParentId(), parentId)).collect(Collectors.toList());
        // 递归下一级
        departments.forEach(p -> p.setChildren(getChildren(allNode, p.getId())));

        return departments;
    }

    /**
     * 获取最高父节点
     * @param allNode
     * @return
     */
    public static <T extends TreeEntity> List<T> selectRootNodeData(List<T> allNode) {

        return allNode.stream().filter(item -> Objects.equals(item.getParentId(), 0L)).collect(Collectors.toList());
    }
}
