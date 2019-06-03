package com.hlx.sell.controller;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hlx.sell.VO.ResultVo;
import com.hlx.sell.converter.OrderForm2OrderDTOController;
import com.hlx.sell.dataobject.OrderDetail;
import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.form.OrderForm;
import com.hlx.sell.service.BuyerService;
import com.hlx.sell.service.OrderService;
import com.hlx.sell.service.WebSocket;
import com.hlx.sell.utils.ResultUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    //创建订单
        @Autowired
        private OrderService orderService;
        @Autowired
        private BuyerService buyerService;
        @Autowired
        private WebSocket webSocket;

        @PostMapping("/create")
        public ResultVo<Map<String,String >> create(@Valid OrderForm orderForm, BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                log.error("[创建订单]创建订单校检，bingdingResult={}",bindingResult);
                throw new SellException(ResultEnum.ORDER_PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
            }
            OrderDTO orderDTO= OrderForm2OrderDTOController.converter(orderForm);
            if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
                log.error("[创建购物车]创建购物车失败，购物车为空");
                throw new SellException(ResultEnum.CART_EPMPT);
            }
            OrderDTO createResult=orderService.create(orderDTO);
            Map<String,String> map=new HashMap<>();
            map.put("orderId",createResult.getOrderId());
            webSocket.senndMessage("你有新订单！");
            return ResultUtils.success(map);
        }
    //订单列表
    @PostMapping("/list")
        public ResultVo<List<OrderDTO>> findlist(@RequestParam("openid") String openid,
                                                 @RequestParam(value = "page",defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size",defaultValue = "10") Integer size){
            if (StringUtils.isEmpty(openid)){
                log.error("[参数错误]openid为空");
                throw new SellException(ResultEnum.ORDER_PARAM_ERROR);
            }
            PageRequest request= new PageRequest(page,size);
            Page<OrderDTO> orderDTO=orderService.findByOpenId(openid,request);
            return ResultUtils.success(orderDTO.getContent());
        }

    //订单详情
    @GetMapping("/detail")
    public ResultVo<List<OrderDTO>> finddetial(@RequestParam String openid,@RequestParam String orderid){

          OrderDTO orderDTO= buyerService.findOrderOne(openid,orderid);
            return ResultUtils.success(orderDTO);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVo cancel(@RequestParam String openid,@RequestParam String orderid){

        buyerService.cancelOrder(openid,orderid);
        return ResultUtils.success();
    }
}
