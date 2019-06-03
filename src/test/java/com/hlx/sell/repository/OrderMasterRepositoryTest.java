package com.hlx.sell.repository;

import com.hlx.sell.dataobject.OrderMaster;
import com.hlx.sell.eums.OrderStatusEnum;
import com.hlx.sell.eums.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository masterRepository;

    @Test
    public void findByOpenId() {
    }
    @Test
    public void save(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1202245433211");
        orderMaster.setBuyerName("苍井空");
        orderMaster.setBuyerPhone("123645685725");
        orderMaster.setBuyerAddress("古盯大道");
        orderMaster.setBuyerOpenid("123645685725");
        orderMaster.setOrderAmount(new BigDecimal(28));
        orderMaster.setOrderStatus(OrderStatusEnum.PAID.getCode());
        orderMaster.setPayStatus(PayStatus.PAYED.getCode());
        OrderMaster result=masterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }
}