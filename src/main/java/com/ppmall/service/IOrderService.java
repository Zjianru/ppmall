package com.ppmall.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ppmall.common.ServerResponse;
import com.ppmall.vo.OrderVo;


/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface IOrderService{

    /**
     * 创建 Order
     * @param userId 用户 Id
     * @param shippingId 收货地址 Id
     * @return ServerResponse
     */
    ServerResponse createOrder(Integer userId, Integer shippingId);

    /**
     * 取消订单
     * @param userId 用户Id
     * @param orderNo 订单号
     * @return ServerResponse<String>
     */
    ServerResponse<String> cancel(Integer userId, Long orderNo);

    /**
     * 获得订单购物车产品内容
     * @param userId 用户Id
     * @return ServerResponse
     */
    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 获取订单详情
     * @param userId 用户 Id
     * @param orderNo 订单号
     * @return ServerResponse<OrderVo
     */
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    /**
     * 分页详情
     * @param userId 用户Id
     * @param pageNum 页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    /**
     * 支付宝接口调用
     * @param orderNo 订单号
     * @param userId 用户 Id
     * @param path 路径
     * @return ServerResponse
     */
    ServerResponse pay(Long orderNo, Integer userId, String path);
    /**
     * 阿里回调
     * @param params Map<String,String> params
     * @return ServerResponse
     */
    ServerResponse aliCallBack(Map<String, String> params);

    /**
     * 查询订单支付状态
     * @param userId 用户Id
     * @param orderNo 订单号
     * @return ServerResponse
     */
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);



    // backend


    /**
     * 后台订单List分页
     * @param pageNum 页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    /**
     * 后台获得详情
     * @param orderNo 订单号
     * @return ServerResponse<OrderVo>
     */
    ServerResponse<OrderVo>manageDetail(Long orderNo);

    /**
     * 后台搜索（分页）
     * @param orderNo 订单号
     * @param pageNum 页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo>manageSearch(Long orderNo, int pageNum, int pageSize);

    /**
     * 发货
     * @param orderNo 订单号
     * @return ServerResponse<String>
     */
    ServerResponse<String> manageSendGoods(Long orderNo);
}
