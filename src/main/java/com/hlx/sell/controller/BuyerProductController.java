package com.hlx.sell.controller;

import com.hlx.sell.VO.ProductInfoVo;
import com.hlx.sell.VO.ProductVo;
import com.hlx.sell.VO.ResultVo;
import com.hlx.sell.dataobject.ProductCategory;
import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.service.ProductCategoryService;
import com.hlx.sell.service.ProductInfoService;
import com.hlx.sell.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
        @Autowired

        private ProductInfoService productInfoService;

        @Autowired
        private ProductCategoryService productCategoryService;


    @GetMapping("/list")
//    @Cacheable(cacheNames = "product" ,key = "123")
    public ResultVo list(){

//        1.查询销售中商品
        List<ProductInfo> productInfoList=productInfoService.findUpAll();
//        2.查询类目（一次性查询）
        List<Integer> categoryTpyeList=new ArrayList<>();
        for( ProductInfo productInfo : productInfoList){
            categoryTpyeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList=productCategoryService.findByCategoryTypeIn(categoryTpyeList);
//        3.拼装数据
        List<ProductVo> productVoList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVo productVo=new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVoList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                   ProductInfoVo productInfoVo=new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultUtils.success(productVoList);

    }
}
