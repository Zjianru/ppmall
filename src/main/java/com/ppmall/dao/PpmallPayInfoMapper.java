package com.ppmall.dao;

import com.ppmall.pojo.PpmallPayInfo;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.dao
 * 2019/1/15
 */
public interface PpmallPayInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PpmallPayInfo record);

    int insertSelective(PpmallPayInfo record);

    PpmallPayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallPayInfo record);

    int updateByPrimaryKey(PpmallPayInfo record);
}