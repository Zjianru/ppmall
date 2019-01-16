package com.ppmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallShipping;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.IShippingService;
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
 * com.ppmall.controller.protal
 * 2019/1/16
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    IShippingService iShippingService;

    /**
     * 添加地址
     *
     * @param session  session 信息
     * @param shipping 地址对象
     * @return ServerResponse
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, PpmallShipping shipping) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);
    }

    /**
     * 删除地址
     *
     * @param session    session 信息
     * @param shippingId 地址 Id
     * @return ServerResponse
     */
    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session, Integer shippingId) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId);
    }

    /**
     * 更新地址
     *
     * @param session  session 信息
     * @param shipping 地址对象
     * @return ServerResponse
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, PpmallShipping shipping) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping);
    }

    /**
     * 选中查看具体的地址详情
     *
     * @param session    session 信息
     * @param shippingId 地址 Id
     * @return ServerResponse<PpmallShipping>
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<PpmallShipping> select(HttpSession session, Integer shippingId) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId);
    }

    /**
     * 分页 查询地址详情
     *
     * @param pageNum  页数 默认 1
     * @param pageSize 每页容量 默认 10
     * @param session  session 信息
     * @return ServerResponse<PageInfo>
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpSession session) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(pageNum, pageSize, user.getId());
    }

}
