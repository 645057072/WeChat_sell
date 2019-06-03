package com.hlx.sell.config;


import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatPayConfig {


    @Autowired
    private WechatAccountConfiguration accountConfiguration;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());

        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config=new WxPayH5Config();
        wxPayH5Config.setAppId(accountConfiguration.getMpAppId());
        wxPayH5Config.setAppSecret(accountConfiguration.getMpAppSecret());
        wxPayH5Config.setMchId(accountConfiguration.getMchId());
        wxPayH5Config.setMchKey(accountConfiguration.getMchKey());
        wxPayH5Config.setKeyPath(accountConfiguration.getMchPath());
        wxPayH5Config.setNotifyUrl(accountConfiguration.getNotifyUrl());
        return wxPayH5Config;

    }
}
