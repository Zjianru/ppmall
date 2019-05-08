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
public class OrderItemVo {

    /** order No */
    private Long orderNo;

    /** 商品id*/
    private Integer productId;

    /** 商品名称*/
    private String productName;

    /** 商品图片地址*/
    private String productImage;

    /** 生成订单时的商品单价，单位是元,保留两位小数*/
    private BigDecimal currentUnitPrice;

    /** 商品数量*/
    private Integer quantity;

    /** 商品总价,单位是元,保留两位小数*/
    private BigDecimal totalPrice;

    /** create Time  */
    private String createTime;

}