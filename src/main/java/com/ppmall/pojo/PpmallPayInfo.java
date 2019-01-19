package com.ppmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
public class PpmallPayInfo implements Serializable {
    /** id */
    private Integer id;

    /** 用户id */
    private Integer userId;

    /** 订单号 */
    private Long orderNo;

    /** 支付平台:1-支付宝,2-微信 */
    private Integer payPlatform;

    /** 支付宝支付流水号 */
    private String platformNumber;

    /** 支付宝支付状态 */
    private String platformStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}