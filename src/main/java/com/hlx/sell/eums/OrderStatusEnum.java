package com.hlx.sell.eums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    CANCLELLED(0,"已取消"),
    NEW_ORDER(10,"新订单"),
    PAID(20,"已支付"),
    SHIPPED(40,"已发货"),
    ORDER_SUCCESS(50,"订单完成"),
    ORDER_CLOSE(60,"订单关闭");



    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
