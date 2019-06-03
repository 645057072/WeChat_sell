package com.hlx.sell.eums;

import lombok.Getter;

@Getter
public enum PayStatus implements CodeEnum{

    NO_PAY(0,"未支付"),
    PAYED(1,"已支付"),
    REFUND(2,"已退款")
    ;

    private Integer code;
    private String message;

    PayStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
