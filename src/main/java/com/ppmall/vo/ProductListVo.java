package com.ppmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.vo
 * 2019/1/15
 */
@Data
public class ProductListVo {
    /**
     * ID
     */
    private Integer id;
    /**
     * 分类Id
     */
    private Integer categoryid;

    /**
     * 名字
     */
    private String name;

    /**
     * 子标题
     */
    private String subtitle;
    /**
     * 主图
     */
    private String mainImage;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 图片主机前缀
     */
    private String imageHost;
}
