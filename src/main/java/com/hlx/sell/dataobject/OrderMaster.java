package com.hlx.sell.dataobject;

import com.hlx.sell.eums.OrderStatusEnum;
import com.hlx.sell.eums.PayStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
@Table(name = "order_master")
public class OrderMaster {
    @Id
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
    private Integer orderStatus= OrderStatusEnum.NEW_ORDER.getCode();
    //支付状态
    private Integer payStatus= PayStatus.NO_PAY.getCode();

    private Date createTime;

    private Date updateTime;
}
