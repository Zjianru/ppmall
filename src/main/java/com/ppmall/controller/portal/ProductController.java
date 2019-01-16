package com.ppmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.ppmall.common.ServerResponse;
import com.ppmall.service.IProductService;
import com.ppmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.controller.protal
 * 2019/1/16
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    /**
     * 前端获取在线商品信息
     * @param productId 商品Id
     * @return ServerResponse<ProductDetailVo>
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
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
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(
            @RequestParam(value = "keyword",required = false)String keyword,
            @RequestParam(value = "categoryId",required = false) Integer categoryId,
            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
            @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
    return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}