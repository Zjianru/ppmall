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

    /**
     * 根据主键删除
     * @param id id
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insert(PpmallShipping record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallShipping record);

    /**
     * 根据主键查找
     * @param id id
     * @return 封装好信息的对象
     */
    PpmallShipping selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallShipping record);

    /**
     * 根据主键更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallShipping record);

    /**
     *
     * @param userId userId
     * @param shippingId shippingId
     * @return 影响行数
     */

    int deleteByShippingIdAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     *  根据 ShippingId 更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByShipping(PpmallShipping record);

    /**
     * 根据 UserId 和 shippingId 查找
     * @param userId userId
     * @param shippingId shippingId
     * @return PpmallShipping
     */
    PpmallShipping selectByShippingIdAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 根据 UserId 查找
     * @param userId userId
     * @return List<PpmallShipping>
     */
    List<PpmallShipping> selectByUserId(@Param("userId") Integer userId);
}