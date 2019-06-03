package com.hlx.sell.service.Impl;

import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.repository.OrderMasterRepository;
import com.hlx.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PushMessageServiceServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Test
    public void orderStatus() {
        OrderDTO orderDTO=orderService.findByOrderId("1555309289327592849");
       pushMessageService.OrderStatus(orderDTO);

    }
}