package com.hlx.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hlx.sell.eums.ProductStatusEnum;
import com.hlx.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name = "product_info")
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -3872601893153340261L;
    @Id
    private String productId;
    //名字
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
    private Integer productStatus=ProductStatusEnum.UP.getCode();
    //类目
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;




    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }

}
