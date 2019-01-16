package com.ppmall.dao;
import com.ppmall.pojo.PpmallOrderItem;
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
public interface PpmallOrderItemMapper {

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
    int insert(PpmallOrderItem record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallOrderItem record);

    /**
     * 根据主键查找
     * @param id id
     * @return 封装好信息的对象
     */
    PpmallOrderItem selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallOrderItem record);

    /**
     * 更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallOrderItem record);

    /**
     * 根据订单号和 UserId 查找
     * @param orderNo orderNo
     * @param userId userId
     * @return List<PpmallOrderItem>
     */
    List<PpmallOrderItem> findByOrderNoAndUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    /**
     * 插入列表
     * @param list list
     * @return 影响行数
     */
    int insertList(@Param("list") List<PpmallOrderItem> list);

    /**
     * 根据订单号查找
     * @param orderNo orderNo
     * @return List<PpmallOrderItem>
     */
    List<PpmallOrderItem> findByOrderNo(@Param("orderNo") Long orderNo);

}