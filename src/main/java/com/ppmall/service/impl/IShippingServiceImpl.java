package com.ppmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ppmall.common.ServerResponse;
import com.ppmall.dao.PpmallShippingMapper;
import com.ppmall.pojo.PpmallShipping;
import com.ppmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service.impl
 * 2019/1/16
 */
@Service("iShippingService")
public class IShippingServiceImpl implements IShippingService {

    @Autowired
    private PpmallShippingMapper shippingMapper;

    /**
     * 添加地址
     *
     * @param userId   用户Id
     * @param shipping 地址 对象
     * @return ServerResponse
     */
    @Override
    public ServerResponse add(Integer userId, PpmallShipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map<Object, Object> result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功", result);
        }
        return ServerResponse.createByERRORMessage("新建地址失败");
    }

    /**
     * 删除地址
     *
     * @param userId     用户 Id
     * @param shippingId 地址 Id
     * @return ServerResponse
     */
    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdAndUserId(userId, shippingId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByERRORMessage("删除地址失败");
    }

    /**
     * 更新地址
     *
     * @param userId   用户Id
     * @param shipping 地址对象
     * @return ServerResponse
     */
    @Override
    public ServerResponse update(Integer userId, PpmallShipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByERRORMessage("更新地址失败");
    }

    /**
     * 选中查看具体的地址详情
     *
     * @param userId     用户 Id
     * @param shippingId 地址 Id
     * @return ServerResponse<PpmallShipping>
     */
    @Override
    public ServerResponse<PpmallShipping> select(Integer userId, Integer shippingId) {
        PpmallShipping shipping = shippingMapper.selectByShippingIdAndUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByERRORMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("查询到地址", shipping);
    }

    /**
     * 分页 查询地址详情
     * @param pageNum 页数 默认 1
     * @param pageSize 每页容量 默认 10
     * @param userId 用户 Id
     * @return ServerResponse<PageInfo>
     */
    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<PpmallShipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);

    }


}
