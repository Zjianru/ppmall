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
    int deleteByPrimaryKey(Integer id);

    int insert(PpmallOrder record);

    int insertSelective(PpmallOrder record);

    PpmallOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallOrder record);

    int updateByPrimaryKey(PpmallOrder record);

    PpmallOrder findOneByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    PpmallOrder findByOrderNo(@Param("orderNo") Long orderNo);

    List<PpmallOrder> findByUserId(@Param("userId") Integer userId);

    List<PpmallOrder> find();

}