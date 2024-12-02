package com.cn.zooey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResPage;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.util.PageUtil;
import com.cn.zooey.convert.OperBtnConvert;
import com.cn.zooey.entity.OperBtn;
import com.cn.zooey.repository.OperBtnRepoistory;
import com.cn.zooey.service.OperBtnService;
import com.cn.zooey.vo.OperBtnListVO;
import com.cn.zooey.vo.OperBtnVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 操作按钮表 服务实现类
 * </p>
 *
 * @author Fengzl
 * @since 2023-10-17
 */
@Service
public class OperBtnServiceImpl implements OperBtnService {

    @Resource
    private OperBtnRepoistory operBtnRepoistory;


    @Override
    public ResResult<ResPage<OperBtn>> listOperBtn(OperBtnListVO operBtnListVO) {
        LambdaQueryWrapper<OperBtn> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(operBtnListVO.getBtnName())) {
            queryWrapper.like(OperBtn::getBtnName, operBtnListVO.getBtnName());
        }

        IPage<OperBtn> iPage = PageUtil.toIPage(operBtnListVO.getPageable());
        IPage<OperBtn> operBtnIPage = operBtnRepoistory.page(iPage, queryWrapper);

        return ResResult.ok(new ResPage<>(operBtnIPage));
    }

    @Override
    public ResResult<?> addOperBtn(OperBtnVO operBtnVO) {

        OperBtn operBtn = OperBtnConvert.INSTANCE.toOperBtn(operBtnVO);
        operBtnRepoistory.save(operBtn);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> updateOperBtn(OperBtnVO operBtnVO) {
        OperBtn operBtn = operBtnRepoistory.getById(operBtnVO.getId());
        if (Objects.isNull(operBtn)) {
            throw new SaasException("权限点不存在");
        }
        OperBtnConvert.INSTANCE.updateOperBtn(operBtnVO, operBtn);
        operBtnRepoistory.updateById(operBtn);

        return ResResult.ok();
    }

    @Override
    public ResResult<?> removeOperBtn(Long id) {
        boolean b = operBtnRepoistory.removeById(id);
        if (!b) {
            throw new SaasException("权限点不存在或已删除");
        }
        return ResResult.ok();
    }

    @Override
    public ResResult<?> endisableOperBtn(Long id, Byte state) {

        OperBtn operBtn = operBtnRepoistory.getById(id);

        Optional.ofNullable(operBtn).filter(p -> !Objects.equals(p.getState(), state)).orElseThrow(() -> new SaasException("权限点不存在或不支持此操作"));

        operBtn.setState(state);
        operBtnRepoistory.updateById(operBtn);

        return ResResult.ok();
    }
}
