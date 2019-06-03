package com.hlx.sell.service;

import com.hlx.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface PayService {
//支付
    PayResponse create(OrderDTO orderDTO);
//    异步通知
    PayResponse notify(@RequestParam String notifyData);

//    退款
    RefundResponse refund(OrderDTO orderDTO);

}
