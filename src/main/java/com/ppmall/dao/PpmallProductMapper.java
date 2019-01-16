package com.ppmall.dao;
import com.ppmall.pojo.PpmallProduct;
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
public interface PpmallProductMapper {

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
    int insert(PpmallProduct record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallProduct record);

    /**
     * 根据主键查找
     * @param id id
     * @return 封装好信息的对象
     */
    PpmallProduct selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallProduct record);

    /**
     * updateByPrimaryKeyWithBLOBs
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeyWithBLOBs(PpmallProduct record);

    /**
     *  根据主键更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallProduct record);

    /**
     * 查找
     * @return List<PpmallProduct>
     */
    List<PpmallProduct> selectList();

    /**
     * 根据 Name 和 Id 查找
     * @param productName productName
     * @param productId productId
     * @return List<PpmallProduct>
     */
    List<PpmallProduct> selectByNameAndId(@Param("productName") String productName, @Param("productId") Integer productId);

    /**
     * selectByNameAndCategoryIds
     * @param productName productName
     * @param categoryIdList categoryIdList
     * @return List<PpmallProduct>
     */
    List<PpmallProduct> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);
}