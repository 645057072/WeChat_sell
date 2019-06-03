package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.eums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findById() {
        List<ProductInfo> productInfos=productInfoService.findAllById("2001");
        Assert.assertEquals(1,productInfos.size());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList=productInfoService.findUpAll();
        Assert.assertEquals(2,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> infoPage=productInfoService.findAll(request);
        Assert.assertNotEquals(0,infoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("2005");
        productInfo.setProductName("佛跳墙");
        productInfo.setProductPrice(new BigDecimal(199.99));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("滋阴补阳，鲜美无比");
        productInfo.setProductIcon("http://xxxx.jag");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(3);
        ProductInfo result=productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}