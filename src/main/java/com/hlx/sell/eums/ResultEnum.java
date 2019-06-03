package com.hlx.sell.eums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NO_EXEITS(5,"产品不存在"),
    PRODUCT_STOCK_ERROR(4,"库存数量错误"),
    ORDERMASTER_NO_EXEITS(3,"订单不存在"),
    ORDERDETAIL_NO_EXEITS(3,"订单明细不存在"),
    ORDER_STUTAS_ERROR(2,"订单状态不正确"),
    ORDER_UPDATE_ERROR(1,"更新订单失败"),
    ORDERLIST_NO_EXEITS(0,"订单详情不存在"),
    ORDER_PAY_NO_FINISH(6,"订单未完成支付"),
    ORDER_PAY_ERROR(7,"支付状态错误"),
    ORDER_PARAM_ERROR(8,"参数错误"),
    CART_EPMPT(9,"购物车为空"),
    WECHAT_MP_ERROR(10,"微信调用方面错误"),
    NOTIFY_AMOUNT_ERROR(11,"支付金额与订单金额不一致"),
    ORDER_CANCEL_SUCCESS(12,"取消订单成功"),
    ORDER_FINISH_SUCCESS(13,"订单完结成功"),
    PRODUCT_PARAM_ERROR(14,"产品参数错误"),
    PRODUCT_OFFSALE_SUCCESS(15,"产品下架成功"),
    PRODUCT_ONSALE_SUCCESS(16,"产品上架成功"),
    CATEGORY_NO_EXEITS(17,"类目不存在"),
    LOGIN_FAIL(18,"登录失败"),
    LOGOUT(19,"退出成功"),
    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
