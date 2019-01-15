package com.ppmall.util;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.util
 * 2019/1/15
 */
public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    /**
     * 高精度数的加法
     * @param value1 double 数字
     * @param value2 double 数字
     * @return BigDecimal
     */
    public static BigDecimal add(double value1, double value2){
        BigDecimal re1 = new BigDecimal(Double.toString(value1));
        BigDecimal re2 = new BigDecimal(Double.toString(value2));
        return re1.add(re2);
    }

    /**
     * 高精度数的减法
     * @param value1 double 数字
     * @param value2 double 数字
     * @return BigDecimal
     */
    public static BigDecimal sub(double value1, double value2){
        BigDecimal re1 = new BigDecimal(Double.toString(value1));
        BigDecimal re2 = new BigDecimal(Double.toString(value2));
        return re1.subtract(re2);
    }

    /**
     * 高精度数的乘法
     * @param value1 double 数字
     * @param value2 double 数字
     * @return BigDecimal
     */
    public static BigDecimal mul(double value1, double value2){
        BigDecimal re1 = new BigDecimal(Double.toString(value1));
        BigDecimal re2 = new BigDecimal(Double.toString(value2));
        return re1.multiply(re2);
    }

    /**
     * 高精度数的除法 四舍五入保留两位小数
     * @param value1 double 数字
     * @param value2 double 数字
     * @return BigDecimal
     */
    public static BigDecimal div(double value1, double value2){
        BigDecimal re1 = new BigDecimal(Double.toString(value1));
        BigDecimal re2 = new BigDecimal(Double.toString(value2));
        // 默认进行四舍五入
        return re1.divide(re2,BigDecimal.ROUND_HALF_UP);
    }
}
