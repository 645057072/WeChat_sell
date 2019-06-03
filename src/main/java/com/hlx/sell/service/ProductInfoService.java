package com.hlx.sell.service;

import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductInfoService {
    ProductInfo findByProductId(String productId);
    List<ProductInfo> findAllById(String productId);
    //查询在线销售的产品
   List<ProductInfo> findUpAll();
    //查询所有的产品
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}
