package com.ppmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ppmall.common.Const;
import com.ppmall.common.ServerResponse;
import com.ppmall.dao.*;
import com.ppmall.pojo.*;
import com.ppmall.service.IOrderService;
import com.ppmall.util.BigDecimalUtil;
import com.ppmall.util.DateTimeUtil;
import com.ppmall.util.FTPUtil;
import com.ppmall.util.PropertiesUtil;
import com.ppmall.vo.OrderItemVo;
import com.ppmall.vo.OrderProductVo;
import com.ppmall.vo.OrderVo;
import com.ppmall.vo.ShippingVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service.impl
 * 2019/1/16
 */
@Service("iOrderService")
public class IOrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(IOrderServiceImpl.class);
    @Autowired
    PpmallCartMapper cartMapper;
    @Autowired
    PpmallPayInfoMapper payInfoMapper;
    @Autowired
    PpmallProductMapper productMapper;
    @Autowired
    private PpmallOrderMapper orderMapper;
    @Autowired
    private PpmallOrderItemMapper orderItemMapper;
    @Autowired
    private PpmallShippingMapper shippingMapper;

    /**
     * 创建 Order
     *
     * @param userId     用户 Id
     * @param shippingId 收货地址 Id
     * @return ServerResponse
     */
    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        //从购物车中获取数据
        List<PpmallCart> cartList = cartMapper.findCheckedByUserId(userId);
        // 计算总价
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<PpmallOrderItem> orderItemList = (List<PpmallOrderItem>) serverResponse.getData();
        BigDecimal payment = getOrderTotalPrice(orderItemList);
        // 生成订单
        PpmallOrder order = asSimpleOrder(userId, shippingId, payment);
        if (order == null) {
            return ServerResponse.createByERRORMessage("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ServerResponse.createByERRORMessage("购物车为空");
        }
        for (PpmallOrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        // MyBatis 批量插入
        int rowCount = orderItemMapper.insertList(orderItemList);
        if (rowCount < 0) {
            return ServerResponse.createByERRORMessage(" ERROR ");
        }
        // 生成成功，减少库存
        reduceProductStock(orderItemList);
        // 减少购物车
        cleanCart(cartList);
        // 返回明细
        OrderVo orderVo = asSimpleOrderVo(order, orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 取消订单
     *
     * @param userId  用户Id
     * @param orderNo 订单号
     * @return ServerResponse<String>
     */
    @Override
    public ServerResponse<String> cancel(Integer userId, Long orderNo) {
        PpmallOrder order = orderMapper.findOneByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByERRORMessage("订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return ServerResponse.createByERRORMessage("已付款，无法退款");
        }
        PpmallOrder updateOrder = new PpmallOrder();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (row > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 获得订单购物车产品内容
     *
     * @param userId 用户Id
     * @return ServerResponse
     */
    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo();
        // 从购物车中获取数据
        List<PpmallCart> cartList = cartMapper.findCheckedByUserId(userId);
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<PpmallOrderItem> orderItemList = (List<PpmallOrderItem>) serverResponse.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (PpmallOrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(asSimpleOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(orderProductVo);
    }

    /**
     * 获取订单详情
     *
     * @param userId  用户 Id
     * @param orderNo 订单号
     * @return ServerResponse<OrderVo
                    */
    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        PpmallOrder order = orderMapper.selectByPrimaryKey(userId);
        if (order != null) {
            List<PpmallOrderItem> orderItemList = orderItemMapper.findByOrderNoAndUserId(orderNo, userId);
            OrderVo orderVo = asSimpleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByERRORMessage("没有找到该订单");
    }

    /**
     * 分页详情
     *
     * @param userId   用户Id
     * @param pageNum  页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    @Override
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PpmallOrder> orderList = orderMapper.findByUserId(userId);
        List<OrderVo> orderVoList = asSimpleOrderVoList(orderList, userId);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 支付宝接口调用
     *
     * @param orderNo 订单号
     * @param userId  用户 Id
     * @param path    路径
     * @return ServerResponse
     */
    @Override
    public ServerResponse pay(Long orderNo, Integer userId, String path) {
        Map<String, String> resultMap = Maps.newHashMap();
        PpmallOrder order = orderMapper.findOneByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByERRORMessage("用户没有该订单！");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "Ppmall商城扫码支付，订单号：" + outTradeNo;

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String unDiscountableAmount = "0";


        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "订单" + outTradeNo + "购买商品共" + totalAmount + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();

        List<PpmallOrderItem> orderItemList = orderItemMapper.findByOrderNoAndUserId(orderNo, userId);
        for (PpmallOrderItem orderItem : orderItemList) {
            GoodsDetail goodsDetail = GoodsDetail.newInstance(
                    orderItem.getProductId().toString(),
                    orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), 100d).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goodsDetail);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(unDiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))
                // 支付宝回调地址
                .setGoodsDetailList(goodsDetailList);

        Configs.init("zfbinfo.properties");

        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: ");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                File folder = new File(path);
                if (folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
                File targetFile = new File(path, qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error("上传二维码异常", e);
                }
                logger.info("qrPath:" + qrPath);
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                resultMap.put("qrUrl", qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServerResponse.createByERRORMessage("支付宝预下单失败!!!");
            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByERRORMessage("系统异常，预下单状态未知!!!");
            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByERRORMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    /**
     * 阿里回调
     *
     * @param params Map<String,String> params
     * @return ServerResponse
     */
    @Override
    public ServerResponse aliCallBack(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        PpmallOrder order = orderMapper.findByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.createByERRORMessage("订单出错，已忽略!");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        PpmallPayInfo payInfo = new PpmallPayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);
        payInfoMapper.insert(payInfo);
        return ServerResponse.createBySuccess();
    }

    /**
     * 查询订单支付状态
     *
     * @param userId  用户Id
     * @param orderNo 订单号
     * @return ServerResponse
     */
    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        PpmallOrder order = orderMapper.findOneByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByERRORMessage("用户没有该订单！");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    // backend

    /**
     * 后台订单List分页
     *
     * @param pageNum  页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    @Override
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PpmallOrder> orderList = orderMapper.find();
        List<OrderVo> orderVoList = asSimpleOrderVoList(orderList, null);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 后台获得详情
     *
     * @param orderNo 订单号
     * @return ServerResponse<OrderVo>
     */
    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        PpmallOrder order = orderMapper.findByOrderNo(orderNo);
        if (order != null) {
            List<PpmallOrderItem> orderItemList = orderItemMapper.findByOrderNo(orderNo);
            OrderVo orderVo = asSimpleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByERRORMessage("订单不存在");
    }

    /**
     * 后台搜索（分页）
     *
     * @param orderNo  订单号
     * @param pageNum  页数
     * @param pageSize 每页容量
     * @return ServerResponse<PageInfo>
     */
    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PpmallOrder order = orderMapper.findByOrderNo(orderNo);
        if (order != null) {
            List<PpmallOrderItem> orderItemList = orderItemMapper.findByOrderNo(orderNo);
            OrderVo orderVo = asSimpleOrderVo(order, orderItemList);
            PageInfo pageInfo = new PageInfo(Lists.newArrayList(order));
            pageInfo.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByERRORMessage("订单不存在");
    }

    /**
     * 发货
     *
     * @param orderNo 订单号
     * @return ServerResponse<String>
     */
    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        PpmallOrder order = orderMapper.findByOrderNo(orderNo);
        if (order != null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByERRORMessage("订单不存在");
    }

// PRIVATE METHODS


    /**
     * 计算订单总价
     *
     * @param orderItemList 订单
     * @return BigDecimal payment
     */
    private BigDecimal getOrderTotalPrice(List<PpmallOrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (PpmallOrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * 简单打印应答
     *
     * @param response AlipayResponse
     */
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    /**
     * 获取购物车详细内容
     *
     * @param userId   用户Id
     * @param cartList 购物车
     * @return ServerResponse<List   <   PpmallOrderItem>>
     */
    private ServerResponse getCartOrderItem(Integer userId, List<PpmallCart> cartList) {
        List<PpmallOrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByERRORMessage("购物车为空");
        }
        // 校验购物车内数据，包括产品的状态和数量
        for (PpmallCart cartItem : cartList) {
            PpmallOrderItem orderItem = new PpmallOrderItem();
            PpmallProduct product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            // 校验产品状态
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                return ServerResponse.createByERRORMessage("产品" + product.getName() + "已被下架");
            }
            // 校验库存
            if (cartItem.getQuantity() > product.getStock()) {
                return ServerResponse.createByERRORMessage("产品" + product.getName() + "库存不足");
            }
            // 组装返回
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    /**
     * 组装/创建订单主逻辑
     *
     * @param userId     用户Id
     * @param shippingId shippingId
     * @param payment    payment
     * @return PpmallOrder
     */
    private PpmallOrder asSimpleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        PpmallOrder order = new PpmallOrder();
        long orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        //todo 发货时间 & 付款时间
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }

    /**
     * 订单号生成
     *
     * @return Long
     */
    private Long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(1000);
    }

    /**
     * 更新库存
     *
     * @param orderItemList orderItemList
     */
    private void reduceProductStock(List<PpmallOrderItem> orderItemList) {
        for (PpmallOrderItem orderItem : orderItemList) {
            PpmallProduct product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 清空购物车
     *
     * @param cartList cartList
     */
    private void cleanCart(List<PpmallCart> cartList) {
        for (PpmallCart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 组装 OrderVo
     *
     * @param order         Order 对象
     * @param orderItemList 详情 List
     * @return OrderVo
     */
    private OrderVo asSimpleOrderVo(PpmallOrder order, List<PpmallOrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        orderVo.setShippingId(order.getShippingId());
        PpmallShipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiveName(shipping.getReceiverName());
            orderVo.setShippingVo(asSimpleShippingVo(shipping));
        }
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (PpmallOrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = asSimpleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * 组装 ShippingVo 对象
     *
     * @param shipping shipping
     * @return ShippingVo
     */
    private ShippingVo asSimpleShippingVo(PpmallShipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        return shippingVo;
    }

    /**
     * 组装 OrderItemVo
     *
     * @param orderItem orderItem
     * @return OrderItemVo
     */
    private OrderItemVo asSimpleOrderItemVo(PpmallOrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    /**
     * List<PpmallOrder> 转化为 List<OrderVo>
     *
     * @param orderList 订单 List
     * @param userId    用户Id
     * @return List<OrderVo>
     */
    private List<OrderVo> asSimpleOrderVoList(List<PpmallOrder> orderList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (PpmallOrder order : orderList) {
            List<PpmallOrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                orderItemList = orderItemMapper.findByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.findByOrderNoAndUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = asSimpleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }
}