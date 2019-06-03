package com.hlx.sell.service;

import com.hlx.sell.dataobject.OrderMaster;
import com.hlx.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    OrderDTO findByOrderId(String orderId);
    Page<OrderDTO> findAll(Pageable pageable);
    Page<OrderDTO> findByOpenId(String buyerOpenid,Pageable pageable);
    OrderDTO cancel(OrderDTO orderDTO);
    OrderDTO finish(OrderDTO orderDTO);
    OrderDTO peid(OrderDTO orderDTO);

}
