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
public class ProductDetailVo {
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
     * 副图
     */
    private String subImages;

    /**
     * 商品详情
     */
    private String detail;
    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 图片主机前缀
     */
    private String imageHost;
    /**
     * 父分类 Id
     */
    private Integer parentCategoryId;
}
