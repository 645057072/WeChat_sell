package com.hlx.sell.repository;

import com.hlx.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    ProductInfo findByProductId(String productId);
    List<ProductInfo> findByProductStatus(Integer productstatus);
}
