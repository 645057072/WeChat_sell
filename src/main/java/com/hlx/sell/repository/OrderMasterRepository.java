package com.hlx.sell.repository;

import com.hlx.sell.dataobject.OrderMaster;
import com.hlx.sell.dto.OrderDTO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    OrderMaster findByOrderId(String orderid);
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenoid,Pageable pageable);
}
