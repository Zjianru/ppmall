package com.ppmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.controller.protal
 * 2019/1/16
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;


    /**
     * 创建订单
     * @param session session 信息
     * @param shippingId 收货地址 Id
     * @return ServerResponse
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpSession session, Integer shippingId) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(),shippingId);
    }

    /**
     * 取消订单
     * @param session session 信息
     * @param orderNo 订单号
     * @return ServerResponse
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpSession session, Long orderNo) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(user.getId(),orderNo);
    }

    /**
     * 获得订单购物车产品内容
     * @param session session 信息
     * @return ServerResponse
     */
    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpSession session) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 获取订单详情
     * @param session session 信息
     * @param orderNo 订单号
     * @return ServerResponse
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session,Long orderNo) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(),orderNo);
    }

    /**
     * 分页查看详情
     * @param session session 信息
     * @param pageNum 页数
     * @param pageSize 每页容量
     * @return ServerResponse
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum ,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(),pageNum,pageSize);
    }


    /**
     * 支付调用
     * @param session session 信息
     * @param orderNo 订单编号
     * @param request request
     * @return ServerResponse
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("/");
        System.out.println("--------------------"+path+"----------------");
        return iOrderService.pay(orderNo,user.getId(),path);
    }


    /**
     * 支付宝回调
     * @param request HttpServletRequest
     * @return Object
     */
    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());

            if(!alipayRSACheckedV2){
                return ServerResponse.createByERRORMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常",e);
        }

        //todo 验证各种数据


        //
        ServerResponse serverResponse = iOrderService.aliCallBack(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单支付状态
     * @param session session 信息
     * @param orderNo 订单号
     * @return ServerResponse<Boolean>
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo) {
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse serverResponse =  iOrderService.queryOrderPayStatus(user.getId(),orderNo);
        if (serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }
}