package com.ppmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.common
 * 2019/1/15
 */
public class Const {

    public  static final String CURRENT_USER = "currentUser";
    public  static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    /**
     * 订单
     */
    public interface Cart{
        /**
         * 选中状态
         */
        int CHECKED = 1;
        /**
         * 未选中状态
         */
        int UN_CHECKED = 0;

        String LIMIT_NUM_FILE = "LIMIT_NUM_FILE";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 权限
     */
    public interface Role{
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;

        /**
         *  管理员
          */
        int ROLE_ADMIN = 1;
    }

    /**
     * 商品状态
     */
    public enum ProductStatusEnum{
        /**
         * 在线状态  1
         */
        ON_SALE("在线", 1);

        private String value;
        private int code;
        ProductStatusEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }
        public String getValue() {
            return value;
        }
        public int getCode() {
            return code;
        }
    }

    /**
     * 订单状态枚举类
     */
    public enum OrderStatusEnum{
        /**
         * 订单状态
         */
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单已完成"),
        ORDER_CLOSE(60,"订单关闭");

        private int code;
        private String value;

        OrderStatusEnum( int code,String value) {
            this.value = value;
            this.code = code;
        }

        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到相应的枚举");
        }

    }

    /**
     * aliPay 回调码状态
     */
    public interface AlipayCallback{
        /**
         * 回调状态
         */
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     * 支付方式
     */
    public enum PayPlatformEnum{
        /**
         * 支付宝
         */
        ALIPAY(1,"支付宝");
        private int code;
        private String value;

        PayPlatformEnum( int code,String value) {
            this.value = value;
            this.code = code;
        }

        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }
    public enum PaymentTypeEnum{
        /**
         * 在线支付
         */
        ONLINE_PAY(1,"在线支付");

        private int code;
        private String value;
        PaymentTypeEnum( int code,String value) {
            this.value = value;
            this.code = code;
        }
        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : values()){
                if (paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到相应的枚举");
        }
    }
}