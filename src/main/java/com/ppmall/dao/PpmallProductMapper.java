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

    int deleteByPrimaryKey(Integer id);

    int insert(PpmallProduct record);

    int insertSelective(PpmallProduct record);

    PpmallProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallProduct record);

    int updateByPrimaryKeyWithBLOBs(PpmallProduct record);

    int updateByPrimaryKey(PpmallProduct record);

    List<PpmallProduct> selectList();

    List<PpmallProduct> selectByNameAndId(@Param("productName") String productName, @Param("productId") Integer productId);

    List<PpmallProduct> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);
}