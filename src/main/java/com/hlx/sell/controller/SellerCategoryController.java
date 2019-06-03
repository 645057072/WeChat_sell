package com.hlx.sell.controller;

import com.hlx.sell.dataobject.ProductCategory;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.form.CategoryForm;
import com.hlx.sell.service.ProductCategoryService;
import com.hlx.sell.utils.KeyUitls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList= categoryService.findAll();
        map.put("categoryList",categoryList);
        return new  ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,Map<String,Object> map){

        if (categoryId!=null){
            ProductCategory  category=categoryService.findByCategoryId(categoryId);
            map.put("category",category);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 创建产品类目
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @GetMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult,Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/errors",map);
        }

        ProductCategory category=new ProductCategory();
        try {
            if (form.getCategoryId()!=null){
                category=categoryService.findByCategoryId(form.getCategoryId());
            }
            BeanUtils.copyProperties(form,category);
            categoryService.save(category);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/errors",map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
