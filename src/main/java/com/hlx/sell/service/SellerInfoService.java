package com.hlx.sell.service;

import com.hlx.sell.dataobject.SellerInfo;

public interface SellerInfoService  {

    /**
     * 查询卖家端信息
     * @param OpenId
     * @return
     */
    SellerInfo findSellerInByOpenId(String OpenId);
}
