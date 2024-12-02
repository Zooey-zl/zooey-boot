package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.Menu;
import com.cn.zooey.mapper.MenuMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 10:47
 * @desc
 **/
@Component
public class MenuRepository extends CrudRepository<MenuMapper, Menu> {
}
