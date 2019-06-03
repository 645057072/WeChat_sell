package com.hlx.sell.form;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductForm {
    private String productId;
    private String productName;
    //单价
    private BigDecimal productPrice;
    //仓库
    private Integer productStock;
    //描述
    private String productDescription;
    //小图
    private String productIcon;
    //状态
    //类目
    private Integer categoryType;
}
