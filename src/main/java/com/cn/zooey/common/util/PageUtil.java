package com.cn.zooey.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.zooey.common.base.vo.JsonPage;

/**
 * @author Fengzl
 * @date 2024/3/21 13:38
 * @desc 分页 相关工具类
 **/
public class PageUtil {


    /**
     * 自定义的 JsonPage 分页转换为 IPage
     * @param pageable
     * @return
     * @param <T>
     */
    public static <T> IPage<T> toIPage(JsonPage pageable) {

        return Page.of(pageable.getPage(), pageable.getSize());
    }
}
