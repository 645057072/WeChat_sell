package com.hlx.sell.controller;


import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.service.Impl.PayServiceImpl;
import com.hlx.sell.service.OrderService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayServiceImpl payService;
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
//         查询订单
        OrderDTO orderDTO=orderService.findByOrderId(orderId);
        if (orderDTO==null){
            throw new SellException(ResultEnum.ORDERMASTER_NO_EXEITS);
        }
//发起支付
        PayResponse payResponse=payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView( "pay/create",map);
    }
    /*
    *
    * 返回给微信处理结果
    * */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestParam String notifyData){
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }
}
