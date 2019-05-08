package com.ppmall.vo;

import lombok.Data;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.vo
 * 2019/1/15
 */
@Data
public class ShippingVo {

    /** 收货姓名 */
    private String receiverName;

    /** 收货固定电话 */
    private String receiverPhone;

    /** 收货移动电话 */
    private String receiverMobile;

    /** 省份 */
    private String receiverProvince;

    /** 城市 */
    private String receiverCity;

    /** 区/县 */
    private String receiverDistrict;

    /** 详细地址 */
    private String receiverAddress;

    /** 邮编 */
    private String receiverZip;
}
