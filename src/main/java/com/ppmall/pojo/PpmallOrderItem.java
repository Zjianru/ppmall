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
public class PpmallOrderItem implements Serializable {
    /** 订单子表id*/
    private Integer id;

    /** user id */
    private Integer userId;

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
    private Date createTime;

    /** update time */
    private Date updateTime;

}