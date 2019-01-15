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

    int deleteByPrimaryKey(Integer id);

    int insert(PpmallOrderItem record);

    int insertSelective(PpmallOrderItem record);

    PpmallOrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallOrderItem record);

    int updateByPrimaryKey(PpmallOrderItem record);

    List<PpmallOrderItem> findByOrderNoAndUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    int insertList(@Param("list") List<PpmallOrderItem> list);

    List<PpmallOrderItem> findByOrderNo(@Param("orderNo") Long orderNo);

}