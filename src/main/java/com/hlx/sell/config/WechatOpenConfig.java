package com.hlx.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfiguration configuration;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService=new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorge());
        return wxOpenService;
        }

    /**
     * 获取微信公众平台OpenId和SecretID信息
     * @return
     */
    @Bean
    public WxMpConfigStorage wxOpenConfigStorge(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage=new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(configuration.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(configuration.getOpenAppSecret());
        return wxMpInMemoryConfigStorage;
    }
}
