package com.ppmall.dao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ppmall.pojo.PpmallOrder;
import org.apache.ibatis.annotations.Param;
/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.dao
 * 2019/1/15
 */
public interface PpmallOrderMapper {
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
    int insert(PpmallOrder record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallOrder record);

    /**
     * 根据主键查找
     * @param id id
     * @return 封装好信息的对象
     */
    PpmallOrder selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallOrder record);

    /**
     * 根据主键更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallOrder record);

    /**
     * 根据 UserId 和订单号查询
     * @param userId userId
     * @param orderNo orderNo
     * @return PpmallOrder
     */
    PpmallOrder findOneByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    /**
     * 根据 orderNo 查找
     * @param orderNo orderNo
     * @return PpmallOrder
     */
    PpmallOrder findByOrderNo(@Param("orderNo") Long orderNo);

    /**
     * 根据 UserId 查找
     * @param userId userId
     * @return List<PpmallOrder>
     */
    List<PpmallOrder> findByUserId(@Param("userId") Integer userId);

    /**
     * 查找
     * @return List<PpmallOrder>
     */
    List<PpmallOrder> find();
}