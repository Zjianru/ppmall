package com.ppmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ppmall.common.ServerResponse;
import com.ppmall.dao.PpmallCategoryMapper;
import com.ppmall.pojo.PpmallCategory;
import com.ppmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service.impl
 * 2019/1/16
 */
@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService {
    

    @Autowired
    PpmallCategoryMapper categoryMapper;



    private Logger logger = LoggerFactory.getLogger(ICategoryServiceImpl.class);

    /**
     * 添加商品品类
     *
     * @param categoryName 品类名
     * @param parentId     上层Id
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByERRORMessage("添加商品参数错误");
        }
        PpmallCategory category = new PpmallCategory();
        category.setParentId(parentId);
        category.setName(categoryName);
        // true —— 代表分类可用
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByERRORMessage("添加品类失败");
    }

    /**
     * 更新商品品类名
     *
     * @param categoryId   商品Id
     * @param categoryName 商品品类名
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByERRORMessage("更新商品参数错误");
        }
        PpmallCategory category = new PpmallCategory();
        category.setId(categoryId);
        category.setName(categoryName);

        int row = categoryMapper.updateByPrimaryKeySelective(category);
        if (row > 0) {
            return ServerResponse.createBySuccess("更新品类名成功");
        }
        return ServerResponse.createByERRORMessage("更新品类名失败 ");
    }

    /**
     * 查找当前分类的子分类，不递归
     * @param categoryId 分类 Id
     * @return ServerResponse<List<PpmallCategory>>
     */
    @Override
    public ServerResponse<List<PpmallCategory>> getChildrenParallelCategory(Integer categoryId) {
        List<PpmallCategory> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的 Id 和孩子节点的 Id
     * @param categoryId 商品类别 Id
     * @return ServerResponse{T data}
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<PpmallCategory> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);
        List<Integer>categoryIdList = Lists.newArrayList();
        if (categoryId != null){
            for (PpmallCategory categoryItem :
                    categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归算出子节点
     * @param categorySet 不重复的Category Set
     * @param categoryId 商品分类Id
     * @return Set<PpmallCategory>
     */
    private Set<PpmallCategory> findChildrenCategory(Set<PpmallCategory> categorySet,Integer categoryId){
        PpmallCategory category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        List<PpmallCategory> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (PpmallCategory categoryItem :
                categoryList) {
            findChildrenCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
