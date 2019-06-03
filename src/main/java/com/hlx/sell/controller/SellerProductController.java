package com.hlx.sell.controller;


import com.hlx.sell.VO.ProductInfoVo;
import com.hlx.sell.dataobject.ProductCategory;
import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.form.ProductForm;
import com.hlx.sell.service.ProductCategoryService;
import com.hlx.sell.service.ProductInfoService;
import com.hlx.sell.utils.KeyUitls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
//    @CacheEvict(cacheNames = "product" ,key = "123")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage=productInfoService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam String productId,Map<String,Object> map){
       try {
           productInfoService.onSale(productId);
       }catch (SellException e){
           log.error("[产品上下架]产品信息不存在={}",e);
           map.put("msg", e.getMessage());
           map.put("url","/sell/seller/product/list");
           return new ModelAndView("common/errors",map);
       }
        map.put("msg",ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 产品下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam String productId,Map<String,Object> map){
        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            log.error("[产品上下架]产品信息不存在={}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/errors",map);
        }
        map.put("msg",ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,Map<String,Object> map){

        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productInfoService.findByProductId(productId);
            map.put("productInfo",productInfo);
        }
       List<ProductCategory> productCategoryList= categoryService.findAll();
       map.put("productCategoryList",productCategoryList);

       return new ModelAndView("product/index",map);
    }

    @GetMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/errors",map);

        }
        ProductInfo productInfo=new ProductInfo();

        try {
            if (!StringUtils.isEmpty(productForm.getProductId())){
                productInfo=productInfoService.findByProductId(productForm.getProductId());
            }else {
                productForm.setProductId(KeyUitls.getqUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);


        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/errors",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }
}
