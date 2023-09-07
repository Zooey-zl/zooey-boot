package com.cn.zooey.common.base.util;

import lombok.Data;

/**
 * @author Fengzl
 * @date 2023/8/31 14:37
 * @desc iP 区域实体
 **/
@Data
public class Region {

    /**
     * ip
     */
    private String ip;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 组织
     */
    private String ISP_;
}
