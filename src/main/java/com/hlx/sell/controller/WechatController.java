package com.hlx.sell.controller;

import com.hlx.sell.config.ProjectUrlConfig;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.lly835.bestpay.rest.type.Get;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String authoRize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWxMpAuthorsizeUrl()+"/sell/wechat/userinfo";
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        return "redirect"+redirectUrl;
    }


    @GetMapping("/userinfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl
                         ){
        WxMpOAuth2AccessToken oAuth2AccessToken=new WxMpOAuth2AccessToken();

        try {
            oAuth2AccessToken= wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权]{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId=oAuth2AccessToken.getOpenId();

        return "redirect"+returnUrl+"openid="+openId;
    }

    @GetMapping("/qrAuthorize")

    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWxOpenAuthorsizeUrl()+"/sell/wechat/qrUserInfo";
        String redirectUrl=wxOpenService.buildQrConnectUrl(url,WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return "redirect"+redirectUrl;
    }

    @GetMapping("qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken=wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e) {
           log.error("[微信网页授权]={}",e);
           throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

        log.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openId=wxMpOAuth2AccessToken.getOpenId();

        return "redirect"+returnUrl+"openId"+openId;
    }

}
