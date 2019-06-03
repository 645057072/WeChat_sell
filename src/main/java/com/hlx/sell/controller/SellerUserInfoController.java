package com.hlx.sell.controller;

import com.hlx.sell.config.ProjectUrlConfig;
import com.hlx.sell.constant.CookieConstant;
import com.hlx.sell.constant.RedisConstant;
import com.hlx.sell.dataobject.SellerInfo;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.service.SellerInfoService;
import com.hlx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserInfoController {

    @Autowired
    private SellerInfoService sellerInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openId,
                              HttpServletResponse response,
                              Map<String,Object> map){
//      1.  查询OPENID是否存在
        SellerInfo sellerInfo=sellerInfoService.findSellerInByOpenId(openId);
        if (sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/errors",map);
        }
//        2.设置TOKEN至REDIS
        String token= UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;
//      获得redis工具链接写入token的K值，V值，String值，过期时间，并且格式化时间。其中TOKEN格式化
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openId, expire, TimeUnit.SECONDS);

//        3.设置TOKEN至COOKIES
//        设置cookie中name（token），value,过期时间
        CookieUtil.set(response,CookieConstant.TOKEN,token, expire);
        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    /**
     * 用户登出
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
//        查询cookie
       Cookie cookie= CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null){

//            清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
//        清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT.getMessage());
        map.put("url","sell/seller/order/list");

        return new ModelAndView("common/success",map);
    }
}
