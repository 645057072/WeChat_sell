package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.SellerInfo;
import com.hlx.sell.repository.SellerInfoRepsitory;
import com.hlx.sell.utils.KeyUitls;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoServiceImplTest {
    private static final String OpenId="abc";

    @Autowired
    private SellerInfoServiceImpl sellerInfoService;




    @Test
    public void findSellerInByOpenId() {
        SellerInfo sellerInfo=sellerInfoService.findSellerInByOpenId(OpenId);
        Assert.assertNotEquals(0,sellerInfo);
    }
}