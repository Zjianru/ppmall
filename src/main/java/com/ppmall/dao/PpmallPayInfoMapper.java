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
    int insert(PpmallPayInfo record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallPayInfo record);

    /**
     * 根据主键查找
     * @param id id
     * @return 封装好信息的对象
     */
    PpmallPayInfo selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallPayInfo record);

    /**
     * 根据主键更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallPayInfo record);
}