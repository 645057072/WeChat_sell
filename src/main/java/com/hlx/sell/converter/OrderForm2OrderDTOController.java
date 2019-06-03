package com.hlx.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlx.sell.dataobject.OrderDetail;
import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTOController {

    public static OrderDTO converter(OrderForm orderForm){
        Gson gson=new Gson();
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderForm.getItrems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("[数据类型转换]转换参数错误,String={}",orderForm.getItrems());
            throw new SellException(ResultEnum.ORDER_PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }


}
