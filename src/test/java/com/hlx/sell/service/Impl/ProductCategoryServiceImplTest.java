package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.ProductCategory;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl service;

    @Test
    public void findAllByIdTest(){
        ProductCategory productCategory=service.findByCategoryId(2);
        System.out.println(productCategory);
    }

    @Test
    public void findAllTest(){
        List<ProductCategory> productCategoryList=service.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3);
        List<ProductCategory> productCategories=service.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategories.size());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory("男生专用",8);
        ProductCategory result=service.save(productCategory);
        Assert.assertNotNull(result);

    }
}