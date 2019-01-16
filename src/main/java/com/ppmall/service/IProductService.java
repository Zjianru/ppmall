package com.ppmall.service;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallProduct;
import com.ppmall.vo.ProductDetailVo;


/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface IProductService {
    /**
     * 更新 / 新增商品
     * @param product product
     * @return ServerResponse Message
     */
    ServerResponse saveOrUpdateProduct(PpmallProduct product);

    /**
     * 修改商品上下架状态
     * @param productId 商品 Id
     * @param status 状态信息
     * @return ServerResponse<String>
     */
    ServerResponse<String> setSellStatus(Integer productId, Integer status);

    /**
     * 获取商品详情
     * @param productId productId
     * @return ServerResponse<ProductDetailVo>
     */
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 获取商品（分页）
     * @param pageNum 分页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 搜索商品(前后台)
     * @param productName 商品名字
     * @param productId 商品 Id
     * @param pageNum 分页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 前台查看商品信息
     * 比后台版本多了一步判断商品在线状态
     * @param productId 商品 Id
     * @return ServerResponse<ProductDetailVo>
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 商品搜索
     * @param keyword 关键字
     * @param categoryId 商品Id
     * @param pageNum 分页数
     * @param pageSize 每页容量
     * @param orderBy 排序
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);

    }
