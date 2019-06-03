package com.hlx.sell.repository;

import com.hlx.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepsitory extends JpaRepository<SellerInfo,String> {

            SellerInfo findSellerInfoByOpenId(String OpenId);
}
