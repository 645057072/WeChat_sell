package com.hlx.sell.controller;

import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.service.Impl.OrderServiceImpl;
import com.hlx.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);
         Page<OrderDTO> orderDTOPage=orderService.findAll(pageRequest);
         map.put("orderDTOPage",orderDTOPage);
         map.put("currentPage",page);
         map.put("size",size);
         return new ModelAndView("order/list",map);

    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam String orderId,Map<String,Object> map){

        try {
            OrderDTO orderDTO=orderService.findByOrderId(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e){
            log.error("[卖家取消订单]查询订单不存在！{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/errors",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 查询订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam String orderId,Map<String,Object> map){
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO=orderService.findByOrderId(orderId);
        }catch (SellException e){
            log.error("[卖家查询订单详情]查询订单详情不存在！{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/errors",map);
        }
            map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public  ModelAndView finish(@RequestParam String orderId,Map<String,Object> map){

        try {
            OrderDTO orderDTO=orderService.findByOrderId(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("[卖家完结订单]查询订不存在！{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/errors",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
