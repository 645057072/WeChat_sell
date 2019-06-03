package com.hlx.sell.service.Impl;

import com.hlx.sell.dataobject.SellerInfo;
import com.hlx.sell.repository.SellerInfoRepsitory;
import com.hlx.sell.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepsitory repsitory;
    @Override
    public SellerInfo findSellerInByOpenId(String OpenId) {
        SellerInfo sellerInfo=repsitory.findSellerInfoByOpenId(OpenId);
        return sellerInfo;
    }
}
