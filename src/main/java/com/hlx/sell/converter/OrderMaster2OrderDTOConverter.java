package com.hlx.sell.converter;

import com.hlx.sell.dataobject.OrderMaster;
import com.hlx.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList=orderMasterList.stream().map(e ->
            converter(e)
        ).collect(Collectors.toList());
        return orderDTOList;
    }
}
