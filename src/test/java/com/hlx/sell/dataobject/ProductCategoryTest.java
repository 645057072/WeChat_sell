package com.hlx.sell.dataobject;

import com.hlx.sell.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
        Optional<ProductCategory> productCategory=repository.findById(4);
        System.out.println(productCategory.toString());
    }

    @Test
    public void addTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("美女榜");
        productCategory.setCategoryType(6);
        repository.save(productCategory);
    }

    @Test
    public void findByProductCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(3,4,6);
        List<ProductCategory> result=repository.findByCategoryTypeIn(list);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }
}