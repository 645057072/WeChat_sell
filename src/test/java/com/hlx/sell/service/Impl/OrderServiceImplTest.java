package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.OrderDetail;
import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
private final String BUYER_OPENEID="110110";
private final String ORDER_ID="1555309289327592849";
    @Autowired
    private OrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("步明秀泽");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerAddress("东京皇宫");
        orderDTO.setBuyerOpenid(BUYER_OPENEID);

        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId("2001");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);
        OrderDetail orderDetail1=new OrderDetail();
        orderDetail1.setProductId("2002");
        orderDetail1.setProductQuantity(1);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result=orderService.create(orderDTO);
        log.info("[创建订单],result={}",result);

    }

    @Test
    public void findByOrderId() {
        OrderDTO resullt=orderService.findByOrderId(ORDER_ID);
        log.info("查询订单 result={}",resullt);
        Assert.assertEquals(ORDER_ID,resullt.getOrderId());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findAll(pageRequest);
        Assert.assertTrue("查询所有订单信息",orderDTOPage.getTotalElements()>0);
    }

    @Test
    public void findByOpenId() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findByOpenId(BUYER_OPENEID,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findByOrderId(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(orderDTO.getOrderStatus(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO=orderService.findByOrderId(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(orderDTO.getOrderStatus(),result.getOrderStatus());
    }

    @Test
    public void peid() throws Exception{
        OrderDTO orderDTO=orderService.findByOrderId(ORDER_ID);
        OrderDTO result=orderService.peid(orderDTO);
        Assert.assertEquals(orderDTO.getPayStatus(),result.getPayStatus());
    }
}