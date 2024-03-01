package com.cn.zooey.common.base.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Fengzl
 * @Date 2023/3/2 16:01
 * @Desc 分页返回体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPage<T> implements Serializable {
    private static final long serialVersionUID = 5769292314150238176L;

    /**
     * 总数量
     */
    @Schema(description = "总数量")
    private Long totalElements;
    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Long totalPages;
    /**
     * 当前页结果集
     */
    @Schema(description = "结果集")
    private List<T> content;

    public ResPage(IPage<T> iPage) {
        this.totalElements = iPage.getTotal();
        this.totalPages = iPage.getCurrent();
        this.content = iPage.getRecords();
    }

}
