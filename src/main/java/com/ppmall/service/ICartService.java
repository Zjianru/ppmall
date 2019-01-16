package com.ppmall.service;


import com.ppmall.common.ServerResponse;
import com.ppmall.vo.CartVo;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface ICartService {
    /**
     * 添加购物车
     * @param userId 用户Id
     * @param count 数量
     * @param productId 商品Id
     * @return ServerResponse<CartVo>
     */
    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);

    /**
     * 更新购物车中商品数量
     * @param userId 用户Id
     * @param count 数量
     * @param productId 商品Id
     * @return ServerResponse<CartVo>
     */
    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    /**
     * 从购物车删除商品
     * @param userId 用户Id
     * @param productIds 商品 Id 集合
     * @return ServerResponse<CartVo>
     */
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    /**
     * 查询购物车内商品
     * @param userId 用户Id
     * @return ServerResponse<CartVo>
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * 全选 / 全部取消选择
     * @param userId 用户Id
     * @param checked 是否选择
     * @return ServerResponse<CartVo>
     */
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId);

    /**
     *  获取当前用户的购物车的产品数量
     * @param userId 用户 Id
     * @return ServerResponse<Integer>
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
