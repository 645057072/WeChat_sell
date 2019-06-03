package com.hlx.sell.service;

import com.hlx.sell.dto.OrderDTO;

public interface PushMessageService {

    void OrderStatus(OrderDTO orderDTO);
}
