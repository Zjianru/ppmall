package com.ppmall.dao;

import com.ppmall.pojo.PpmallShipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.dao
 * 2019/1/15
 */
public interface PpmallShippingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PpmallShipping record);

    int insertSelective(PpmallShipping record);

    PpmallShipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallShipping record);

    int updateByPrimaryKey(PpmallShipping record);

    int deleteByShippingIdAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    int updateByShipping(PpmallShipping record);

    PpmallShipping selectByShippingIdAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    List<PpmallShipping> selectByUserId(@Param("userId") Integer userId);
}