package com.ppmall.vo;



import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.vo
 * 2019/1/15
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    /**
     * 总价
     */
    private BigDecimal cartTotalPrice;
    /**
     * 是否都已勾选
     */
    private Boolean allChecked;

    private String imageHost;

}
