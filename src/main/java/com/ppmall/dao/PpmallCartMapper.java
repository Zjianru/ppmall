package com.ppmall.dao;


import com.ppmall.pojo.PpmallCart;
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
public interface PpmallCartMapper {
    /**
     * 根据主键删除
     * @param id id
     * @return int 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入 -- 需要传入已封装好信息的对象
     * @param record 待插入对象
     * @return 影响行数
     */
    int insert(PpmallCart record);

    /**
     * 选择性插入 -- 需要传入已封装好信息的对象
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallCart record);

    /**
     * 根据主键查找
     * @param id id
     * @return 查找到的对象 / null
     */
    PpmallCart selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 含待更新信息的对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallCart record);

    /**
     * 根据对象更新
     * @param record 含待更新信息的对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallCart record);

    /**
     * 根据UserId 和 ProductId查找 Cart
     * @param userId UserId
     * @param productId productId
     * @return 查到的PpmallCart 对象 / null
     */
    PpmallCart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 根据 UserId 查找 Cart
     * @param userId UserId
     * @return List<PpmallCart>
     */
    List<PpmallCart> selectCartByUserId(@Param("userId") Integer userId);

    /**
     * 根据 UserId 查找已选择状态的 Cart
     * @param userId userId
     * @return 影响行数
     */
    int selectCartProductCheckedStatusByUserId(@Param("userId") Integer userId);

    /**
     * 根据 UserId 和 ProductId 列表删除
     * @param userId userId
     * @param productIdList productId 列表
     * @return 影响行数
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 查找已选择（未选择）状态的商品
     * @param userId userId
     * @param checked 选中状态
     * @param productId productId
     * @return 影响行数
     */
    int checkedOrUnCheckProduct(@Param("userId") Integer userId, @Param("checked") Integer checked, @Param("productId") Integer productId);

    /**
     * 查找数量
     * @param userId userId
     * @return 影响行数
     */
    int selectCartProductCount(@Param("userId") Integer userId);

    /**
     * 用户选中的商品
     * @param userId userId
     * @return List<PpmallCart>
     */
    List<PpmallCart> findCheckedByUserId(@Param("userId") Integer userId);




}