package com.ppmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.dao.PpmallCategoryMapper;
import com.ppmall.dao.PpmallProductMapper;
import com.ppmall.pojo.PpmallCategory;
import com.ppmall.pojo.PpmallProduct;
import com.ppmall.service.ICategoryService;
import com.ppmall.service.IProductService;
import com.ppmall.util.DateTimeUtil;
import com.ppmall.util.PropertiesUtil;
import com.ppmall.vo.ProductDetailVo;
import com.ppmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service.impl
 * 2019/1/16
 */
@Service("iProductService")
public class IProductServiceImpl implements IProductService {


    @Autowired
    private PpmallProductMapper productMapper;
    @Autowired
    private PpmallCategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;
    /**
     * 更新 / 新增商品
     * @param product product
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse saveOrUpdateProduct(PpmallProduct product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createByERRORMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createByERRORMessage("更新产品失败");
            }
        }
        return ServerResponse.createByERRORMessage("新增或更新产品的参数不正确！");
    }

    /**
     * 修改商品上下架状态
     *
     * @param productId 商品 Id
     * @param status    状态信息
     * @return ServerResponse<String>
     */
    @Override
    public ServerResponse<String> setSellStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PpmallProduct product = new PpmallProduct();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByERRORMessage("修改产品销售状态失败");
    }

    /**
     * 获取商品详情
     *
     * @param productId 获取商品 Id
     * @return ServerResponse<ProductDetailVo>
     */
    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PpmallProduct product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByERRORMessage("商品已下架或删除！");
        }
        ProductDetailVo productDetailVo = asSimileProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 填充 ProductDetailVo
     *
     * @param product PpmallProduct product
     * @return ProductDetailVo
     */
    private ProductDetailVo asSimileProductDetailVo(PpmallProduct product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryid(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.ztmall.com/"));
        PpmallCategory category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    /**
     * 获取商品（分页）
     *
     * @param pageNum  分页数
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PpmallProduct> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (PpmallProduct productItem : productList) {
            ProductListVo productListVo = asSimileProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 填充 ProductListVo
     *
     * @param product PpmallProduct product
     * @return ProductListVo
     */
    private ProductListVo asSimileProductListVo(PpmallProduct product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryid(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.ztmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    /**
     * 搜索商品(前后台)
     * @param productName 商品名字
     * @param productId 商品 Id
     * @param pageNum 分页数
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<PpmallProduct> productList = productMapper.selectByNameAndId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (PpmallProduct productItem : productList) {
            ProductListVo productListVo = asSimileProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 前台获得商品信息
     * @param productId 商品 Id
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if (productId == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PpmallProduct product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByERRORMessage("商品已下架或删除！");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByERRORMessage("商品已下架或删除！");
        }
        ProductDetailVo productDetailVo = asSimileProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }


    /**
     * 商品搜索
     * @param keyword 关键字
     * @param categoryId 商品Id
     * @param pageNum 分页数
     * @param pageSize 每页容量
     * @param orderBy 排序
     * @return ServerResponse<PageInfo>
     */
    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<>();
        if (categoryId != null) {
            PpmallCategory category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        }
            PageHelper.startPage(pageNum, pageSize);
            if (StringUtils.isNotBlank(orderBy)) {
                if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                    String[] orderByArray = orderBy.split("_");
                    PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
                }
            }
            List<PpmallProduct> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);
            List<ProductListVo> productListVoList = Lists.newArrayList();
            for (PpmallProduct pro : productList) {
                ProductListVo productListVo = asSimileProductListVo(pro);
                productListVoList.add(productListVo);
            }
            PageInfo pageInfo = new PageInfo(productList);
            pageInfo.setList(productListVoList);
            return ServerResponse.createBySuccess(pageInfo);
        }
    }
