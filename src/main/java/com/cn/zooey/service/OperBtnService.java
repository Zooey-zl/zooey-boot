package com.cn.zooey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.entity.OperBtn;
import com.cn.zooey.vo.OperBtnListVO;
import com.cn.zooey.vo.OperBtnVO;

/**
 * <p>
 * 操作按钮表 服务类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
public interface OperBtnService extends IService<OperBtn> {


    /**
     * 权限点列表-分页
     * @param operBtnListVO
     * @return
     */
    ResResult<ResPage<OperBtn>> listOperBtn(OperBtnListVO operBtnListVO);

    /**
     * 新增权限点
     * @param operBtnVO
     * @return
     */
    ResResult<?> addOperBtn(OperBtnVO operBtnVO);

    /**
     * 修改权限点
     * @param operBtnVO
     * @return
     */
    ResResult<?> updateOperBtn(OperBtnVO operBtnVO);

    /**
     * 删除权限点
     * @param id
     * @return
     */
    ResResult<?> removeOperBtn(Long id);


    /**
     * 启用/禁用权限点
     * @param id
     * @param state
     * @return
     */
    ResResult<?> endisableOperBtn(Long id, Byte state);

}
