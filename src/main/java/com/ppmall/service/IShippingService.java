package com.ppmall.service;

import com.github.pagehelper.PageInfo;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallShipping;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface IShippingService {
    /**
     * 添加收获地址
     * @param userId 用户Id
     * @param shipping 地址 对象
     * @return ServerResponse
     */
    ServerResponse add(Integer userId, PpmallShipping shipping);

    /**
     * 删除地址
     * @param userId 用户 Id
     * @param shippingId 地址 Id
     * @return ServerResponse
     */
    ServerResponse del(Integer userId, Integer shippingId);

    /**
     * 更新地址
     * @param userId 用户Id
     * @param shipping 地址对象
     * @return ServerResponse
     */
    ServerResponse update(Integer userId, PpmallShipping shipping);

    /**
     * 选中查看具体的地址详情
     * @param userId 用户 Id
     * @param shippingId 地址 Id
     * @return ServerResponse<PpmallShipping>
     */
    ServerResponse<PpmallShipping> select(Integer userId, Integer shippingId);

    /**
     * 分页 查询地址详情
     * @param pageNum 页数 默认 1
     * @param pageSize 每页容量 默认 10
     * @param userId 用户 Id
     * @return ServerResponse<PageInfo>
     */
    ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId);

}
