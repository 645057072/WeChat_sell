package com.hlx.sell.service.Impl;

import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.service.PayService;
import com.hlx.sell.utils.JsonUtil;
import com.hlx.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;


@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME="微信订单支付";

    @Autowired
    private BestPayServiceImpl  bestPayService;
    @Autowired
    private OrderServiceImpl orderService;
    @Override

    //创建支付
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest request=new PayRequest();
        request.setOpenid(orderDTO.getBuyerOpenid());
        request.setOrderId(orderDTO.getOrderId());
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderName(ORDER_NAME);
        log.info("[微信支付]request={}", JsonUtil.toJson(request));
        PayResponse payResponse=bestPayService.pay(request);
        log.info("[微信支付]payResponse={}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

//    获取异步通知
@Override
    public PayResponse notify(@RequestParam String notifyData){

//        验证签名
//        订单是否存在
//        金额是否正确
//        下单人=支付人
//        修改订单状态
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("[微信支付]微信支付异步通知，payRespone={}",JsonUtil.toJson(payResponse));

        OrderDTO orderDTO=orderService.findByOrderId(payResponse.getOrderId());
        if (orderDTO==null){
            log.error("[微信支付]微信支付订单不存在，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDERMASTER_NO_EXEITS);
        }
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())
        ){
            log.error("[微信支付]微信支付金额不一致，orderId={},异步通知金额={}，微信订单金额={}",
                    orderDTO.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.NOTIFY_AMOUNT_ERROR);
        }

        orderService.peid(orderDTO);

        return payResponse;
    }


    public RefundResponse refund(OrderDTO orderDTO){
        RefundRequest request=new RefundRequest();
        request.setOrderId(orderDTO.getOrderId());
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}",request);
        RefundResponse refundResponse= bestPayService.refund(request);
        log.info("[微信退款] refundResponse={}",refundResponse);
        return refundResponse;
    }
}
