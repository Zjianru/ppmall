package com.ppmall.pojo;

import lombok.*;

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
public class PpmallOrder implements Serializable {
    /** 订单id*/
    private Integer id;

    /** 订单号*/
    private Long orderNo;

    /** 用户id*/
    private Integer userId;

    /** shipping id */
    private Integer shippingId;

    /** 实际付款金额,单位是元,保留两位小数 */
    private BigDecimal payment;

    /** 支付类型,1-在线支付 */
    private Integer paymentType;

    /** 运费,单位是元 */
    private Integer postage;

    /** 订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭 */
    private Integer status;

    /** 支付时间 */
    private Date paymentTime;

    /** 发货时间 */
    private Date sendTime;

    /** 交易完成时间 */
    private Date endTime;

    /** 交易关闭时间 */
    private Date closeTime;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}