package com.hlx.sell.dataobject.mapper;

import com.hlx.sell.SellApplication;
import com.hlx.sell.dataobject.ProductCategory;
import com.hlx.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SellApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class ProductCategoryMapperTest {
    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("categoryName","畅想娱乐榜");
        map.put("categoryType","12");
       int result= mapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject(){
        ProductCategory category=new ProductCategory();
        category.setCategoryName("美女靓装");
        category.setCategoryType(13);
        int result=mapper.insertByObject(category);
        Assert.assertEquals(1, result);

    }

    @Test
    public void findById(){
        ProductCategory productCategory=mapper.findByCategoryType(13);
        Assert.assertNotNull(productCategory);
    }
}