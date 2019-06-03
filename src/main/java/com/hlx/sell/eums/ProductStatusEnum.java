package com.hlx.sell.eums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"销售中"),
    DOWN(1,"未上架");
    private Integer code;
    private String message;


    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
