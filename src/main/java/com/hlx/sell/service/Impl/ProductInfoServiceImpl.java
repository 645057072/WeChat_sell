package com.hlx.sell.service.Impl;

import com.hlx.sell.VO.ProductInfoVo;
import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.dto.CartDTO;
import com.hlx.sell.eums.ProductStatusEnum;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.repository.ProductInfoRepository;
import com.hlx.sell.service.ProductInfoService;
import com.hlx.sell.utils.KeyUitls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    @Cacheable(cacheNames = "product" ,key = "123")
    public ProductInfo findByProductId(String productId) {
        return productInfoRepository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findAllById(String productId) {
        return (List<ProductInfo>) productInfoRepository.findAllById(Collections.singleton(productId));
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "product" ,key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }


    /**
     * 返回库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=productInfoRepository.findByProductId(cartDTO.getProductId());
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NO_EXEITS);
            }
            Integer result=productInfo.getProductStock() + cartDTO.getProductquantity();

            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    /**
     * 减去库存
     * @param cartDTOList
     */


    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
           ProductInfo productInfo= productInfoRepository.findByProductId(cartDTO.getProductId());
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NO_EXEITS);
            }
            Integer result=productInfo.getProductStock() - cartDTO.getProductquantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public ProductInfo onSale(String productId) {

        ProductInfo productInfo=productInfoRepository.findByProductId(productId);
        if (productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NO_EXEITS);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            throw new SellException(ResultEnum.PRODUCT_PARAM_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfo;
    }

    @Override
    @Transactional
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=productInfoRepository.findByProductId(productId);
        if (productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NO_EXEITS);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
            throw new SellException(ResultEnum.PRODUCT_PARAM_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfo;
    }
}
