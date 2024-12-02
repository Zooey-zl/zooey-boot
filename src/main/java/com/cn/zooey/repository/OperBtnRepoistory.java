package com.cn.zooey.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.cn.zooey.entity.OperBtn;
import com.cn.zooey.mapper.OperBtnMapper;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2024/12/2 10:51
 * @desc
 **/
@Component
public class OperBtnRepoistory extends CrudRepository<OperBtnMapper, OperBtn> {
}
