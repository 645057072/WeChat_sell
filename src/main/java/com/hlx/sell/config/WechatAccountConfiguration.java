package com.hlx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfiguration {
    private String mpAppId;
    private String mpAppSecret;

    private String openAppId;

    private String openAppSecret;
//    商户ID
    private String mchId;
//    商户密钥
    private String mchKey;
//    商户证书路径
    private String mchPath;
//异步接收地址
    private String notifyUrl;
}
