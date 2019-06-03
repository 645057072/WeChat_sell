package com.hlx.sell.service.Impl;

import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.service.BuyerService;
import com.hlx.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {

        return checkOrderOnwer(openid,orderid);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {
        OrderDTO orderDTO=checkOrderOnwer(openid,orderid);
        if (orderDTO==null){
            log.error("[查询订单]用户订单不存在");
            throw new SellException(ResultEnum.ORDERMASTER_NO_EXEITS);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOnwer(String openid,String orderid){
        OrderDTO orderDTO=orderService.findByOrderId(orderid);
        if (orderDTO==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[参数错误]用户信息不正确");
            throw new SellException(ResultEnum.ORDER_PARAM_ERROR);
        }
        return orderDTO;
    }
}
