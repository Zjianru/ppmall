package com.ppmall.controller.backend;

import com.google.common.collect.Maps;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallProduct;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.IFileService;
import com.ppmall.service.IProductService;
import com.ppmall.service.IUserService;
import com.ppmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.controller.backend
 * 2019/1/16
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    /**
     * 更改 / 新增商品
     * @param session session 信息
     * @param product product
     * @return ServerResponse
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, PpmallProduct product) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作");
        }
    }

    /**
     * 修改商品上下架状态
     * @param productId 商品 Id
     * @param status    状态信息
     * @return ServerResponse<String>
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSellStatus(HttpSession session, Integer productId, Integer status) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSellStatus(productId, status);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作");
        }
    }

    /**
     * 获取商品详情
     * @param session   session 信息
     * @param productId productId
     * @return ServerResponse Message
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作");
        }
    }

    /**
     * 后台查询展示商品
     * @param session  session 信息
     * @param pageNum  分页数量
     * @param pageSize 每页容量
     * @return ServerResponse Message
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作");
        }
    }

    /**
     * 搜索商品
     * @param session     session 信息
     * @param productName 商品名
     * @param productId   商品 Id
     * @param pageNum     分页数量
     * @param pageSize    每页容量
     * @return ServerResponse Message
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,
                                        String productName,
                                        Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作");
        }
    }

    /**
     * 上传文件
     * @param file 文件
     * @param request request
     * @return ServerResponse
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {

            String path =   request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByERRORMessage("无权限操作");
            }
    }


    /**
     * 富文本上传
     * @param session session 信息
     * @param file 文件
     * @param request request
     * @param response response
     * @return Map
     */
    @RequestMapping("richText_img_upload.do")
    @ResponseBody
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> resultMap = Maps.newHashMap();
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
        resultMap.put("success",false);
        resultMap.put("msg","请登录管理员");
        return resultMap;
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path =   request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            fileMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限！");
            return resultMap;
        }
    }
}