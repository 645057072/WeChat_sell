package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.ProductCategory;
import com.hlx.sell.repository.ProductCategoryRepository;
import com.hlx.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;


    @Override
    @Transactional
    public ProductCategory findByCategoryId(Integer categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
