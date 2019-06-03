package com.hlx.sell.repository;

import com.hlx.sell.dataobject.SellerInfo;
import com.hlx.sell.utils.KeyUitls;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepsitoryTest {
    @Autowired
    private SellerInfoRepsitory repsitory;


    @Test
    public void save(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setId(KeyUitls.getqUniqueKey());
        sellerInfo.setUserName("admin");
        sellerInfo.setOpenId("abc");
        sellerInfo.setPassWord("admin1");
        SellerInfo result=repsitory.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findSellerInfoByOpenId() {
    }
}