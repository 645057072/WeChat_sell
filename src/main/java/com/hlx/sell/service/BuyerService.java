package com.hlx.sell.service;

import com.hlx.sell.dto.OrderDTO;

public interface BuyerService {

    //查询订单
   OrderDTO findOrderOne(String openid,String orderid);

   //取消订单
    OrderDTO cancelOrder(String openid,String orderid);
}
