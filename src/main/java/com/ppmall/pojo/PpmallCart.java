package com.ppmall.pojo;

import lombok.*;


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
@NoArgsConstructor
@AllArgsConstructor
public class PpmallCart implements Serializable {
    /** id */
    private Integer id;

    /** user id */
    private Integer userId;

    /** 商品id*/
    private Integer productId;

    /** 数量*/
    private Integer quantity;

    /** 是否选择,1=已勾选,0=未勾选*/
    private Integer checked;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}