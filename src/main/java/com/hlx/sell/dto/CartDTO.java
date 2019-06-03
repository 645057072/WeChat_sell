package com.hlx.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    private String productId;
    private Integer productquantity;

}
