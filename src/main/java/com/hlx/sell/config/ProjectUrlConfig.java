package com.hlx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projecturls")
public class ProjectUrlConfig {
    /**
     * 微信公众平台URL
     */
    private String wxMpAuthorsizeUrl;
    /**
     * 微信开放平台URL
     */
    private String wxOpenAuthorsizeUrl;

    private String sell;
}
