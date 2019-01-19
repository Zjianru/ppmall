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
public class PpmallShipping implements Serializable {
    /** id */
    private Integer id;

    /** 用户id */
    private Integer userId;

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

    /** create time */
    private Date createTime;

    /** update time */
    private Date updateTime;

}