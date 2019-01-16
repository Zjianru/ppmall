package com.ppmall.controller.backend;

import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.ICategoryService;
import com.ppmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.controller.backend
 * 2019/1/16
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;


    /**
     * 添加品类
     * @param session session 信息
     * @param categoryName 商品品类名
     * @param parentId 商品品类 Id
     * @return ServerResponse Message
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要登录！");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName,parentId);
        } else {
            return ServerResponse.createByERRORMessage("无权限操作！");
        }
    }

    /**
     * 设置商品品类名
     * @param session session 信息
     * @param categoryId 商品品类 Id
     * @param categoryName 商品品类名
     * @return ServerResponse Message
     */

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要登录！");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else {
            return ServerResponse.createByERRORMessage("无权限操作！");
        }
    }

    /**
     * 不递归  获取子节点（平级）
     * @param session session 信息
     * @param categoryId 商品品类 Id
     * @return ServerResponse
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要登录！");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 查询字节点的 Category 信息 —— 不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByERRORMessage("无权限操作！");
        }
    }

    /**
     * 递归获取子节点
     * @param session session 信息
     * @param categoryId 商品品类 Id
     * @return ServerResponse
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要登录！");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 查询当前节点的和递归子节点的 Id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByERRORMessage("无权限操作！");
        }
    }

}
