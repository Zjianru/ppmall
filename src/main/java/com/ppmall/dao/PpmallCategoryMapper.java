package com.ppmall.dao;

import com.ppmall.pojo.PpmallCategory;
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
public interface PpmallCategoryMapper {
    /**
     * 根绝主键删除
     * @param id id
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record 待插入对象
     * @return 影响行数

     */
    int insert(PpmallCategory record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallCategory record);

    /**
     * 根据主键查找
     * @param id id
     * @return PpmallCategory
     */
    PpmallCategory selectByPrimaryKey(Integer id);

    /**
     * 选择性更新信息
     * @param record 含有待更新信息的对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallCategory record);

    /**
     * 更新信息
     * @param record 含有待更新信息的对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallCategory record);

    /**
     * 查找子标题
     * @param parentId parentId 父标题Id
     * @return List<PpmallCategory>
     */
    List<PpmallCategory> selectCategoryChildrenByParentId(@Param("parentId") Integer parentId);


}