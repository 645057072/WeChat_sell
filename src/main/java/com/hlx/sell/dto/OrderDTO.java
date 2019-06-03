package com.hlx.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hlx.sell.dataobject.OrderDetail;
import com.hlx.sell.eums.OrderStatusEnum;
import com.hlx.sell.eums.PayStatus;
import com.hlx.sell.utils.EnumUtil;
import com.hlx.sell.utils.serializer.Data2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class OrderDTO {
    //订单id
    private String orderId;
    //下单人姓名
    private String buyerName;
    //下单人电话
    private String buyerPhone;
    //下单人地址
    private String buyerAddress;
    //下单人微信号
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态
    private Integer orderStatus;
    //支付状态
    private Integer payStatus;

    //调整时间戳最后三位0
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;



    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatus getPayStatus(){
        return EnumUtil.getByCode(payStatus,PayStatus.class);
    }


}
