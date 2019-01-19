package com.ppmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.pojo
 * 2019/1/15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PpmallProduct implements Serializable {
    /** 商品id*/
    private Integer id;

    /** 分类id,对应ppmall_category表的主键 */
    private Integer categoryId;

    /** 商品名称 */
    private String name;

    /** 商品副标题 */
    private String subtitle;

    /** 产品主图,url相对地址 */
    private String mainImage;

    /** 价格,单位-元保留两位小数 */
    private BigDecimal price;

    /** 库存数量 */
    private Integer stock;

    /** 商品状态.1-在售 2-下架 3-删除 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 图片地址,json格式,扩展用 */
    private String subImages;

    /** 商品详情 */
    private String detail;

}