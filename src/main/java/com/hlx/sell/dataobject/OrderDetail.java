package com.hlx.sell.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ToString
@Table(name="order_detail")
public class OrderDetail {
    @Id
    private String detailId;
    //订单主表ID
    private String orderId;
    //产品ID
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    //产品数量
    private Integer productQuantity;
    //产品小图
    private String productIcon;


}
