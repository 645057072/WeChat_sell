package com.hlx.sell.service.Impl;

import com.hlx.sell.converter.OrderMaster2OrderDTOConverter;
import com.hlx.sell.dataobject.OrderDetail;
import com.hlx.sell.dataobject.OrderMaster;
import com.hlx.sell.dataobject.ProductInfo;
import com.hlx.sell.dto.CartDTO;
import com.hlx.sell.dto.OrderDTO;
import com.hlx.sell.eums.OrderStatusEnum;
import com.hlx.sell.eums.PayStatus;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.repository.OrderDetailRepository;
import com.hlx.sell.repository.OrderMasterRepository;
import com.hlx.sell.service.OrderService;
import com.hlx.sell.service.ProductInfoService;
import com.hlx.sell.utils.KeyUitls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayServiceImpl payService;
    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        String orderId=KeyUitls.getqUniqueKey();
//        List<CartDTO> cartDTOList=new ArrayList<>();

        for (OrderDetail orderDetail :orderDTO.getOrderDetailList()){
            ProductInfo productInfo=productInfoService.findByProductId(orderDetail.getProductId());
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NO_EXEITS);
            }

            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);


            orderDetail.setDetailId(KeyUitls.getqUniqueKey());
            orderDetail.setOrderId(orderId);

            BeanUtils.copyProperties(productInfo,orderDetail);

            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatus.NO_PAY.getCode());
        orderMasterRepository.save(orderMaster);
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e
                -> new CartDTO(e.getProductId(),e.getProductQuantity())).
                collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
//        推送消息
        pushMessageService.OrderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(String orderId) {
        //查询订单主表是否存在
          OrderMaster orderMaster=orderMasterRepository.findByOrderId(orderId);
          if (orderMaster==null){
              throw new SellException(ResultEnum.ORDERMASTER_NO_EXEITS);
          }
          //查询订单明细是否存在
          List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
          if (orderDetailList.isEmpty()){
              throw new SellException(ResultEnum.ORDERDETAIL_NO_EXEITS);
          }
          OrderDTO orderDTO=new OrderDTO();
          //将订单明细赋值给orderDTO
          BeanUtils.copyProperties(orderMaster,orderDTO);
          orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询所有的订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasters=orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.converter(orderMasters.getContent());

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasters.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findByOpenId(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
            OrderMaster orderMaster= new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_ORDER.getCode())||orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCLELLED.getCode())){
            log.error("[取消订单]订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }
        //取消订单
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLELLED.getCode());
//        orderDTO.setPayStatus(PayStatus.REFUND.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
            OrderMaster updateResult=orderMasterRepository.save(orderMaster);
            if (updateResult==null){
                log.error("修改订单状态失败,updateResult={}",updateResult);
                throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
            }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]获取订单详情失败！，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDERLIST_NO_EXEITS);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //已支付的订单，返回退款
        if (orderDTO.getPayStatus().equals(PayStatus.PAYED.getCode())){
            //todo
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_ORDER.getCode())){
            log.error("[完结订单状态]修改订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }

        //判断订单支付状态
        if (!orderDTO.getPayStatus().equals(PayStatus.PAYED.getCode())){
            log.error("[完结订单状态]修改订单状态不正确，orderId={},orderPayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_NO_FINISH);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //修改订单账套
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("[更新订单]修改订单状态失败，updateResult={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO peid(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_ORDER.getCode())){
            log.error("[支付订单]订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STUTAS_ERROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatus.NO_PAY.getCode())){
            log.error("[完结订单状态]修改订单状态不正确，orderId={},orderPayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        orderDTO.setPayStatus(PayStatus.PAYED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("[更新订单]修改订单状态失败，updateResult={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
