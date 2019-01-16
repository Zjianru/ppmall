package com.ppmall.service;


import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallCategory;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface ICategoryService {

    /**
     * 添加商品品类
     * @param categoryName 品类名
     * @param parentId 上层Id
     * @return  ServerResponse Message
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新商品品类名
     * @param categoryId 商品Id
     * @param categoryName 商品品类名
     * @return  ServerResponse Message
     */
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 查找当前分类的子分类，不递归
     * @param categoryId 分类 Id
     * @return ServerResponse<List<MmallCategory>>
     */
    ServerResponse<List<PpmallCategory>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询本节点的 Id 和孩子节点的 Id
     * @param categoryId categoryId
     * @return ServerResponse<List<Integer>>
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

}
