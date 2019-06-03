package com.hlx.sell.repository;

import com.hlx.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByCategoryId(Integer CategoryId);
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
