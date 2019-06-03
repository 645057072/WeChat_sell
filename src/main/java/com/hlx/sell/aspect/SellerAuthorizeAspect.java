package com.hlx.sell.aspect;

import com.hlx.sell.constant.CookieConstant;
import com.hlx.sell.constant.RedisConstant;
import com.hlx.sell.exception.SellerAuthorizeException;
import com.hlx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.hlx.sell.controller.Seller*.*(..))"+
            "&& !execution(public * com.hlx.sell.controller.SellerUserInfoController.*(..))")
    public void verify(){

    }

    @Before("verify()")
    public void doverify(){
     ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request=attributes.getRequest();

//        查询COOKIE
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);

        if(cookie==null){
            log.warn("查询COOKIE失败，COOKIE不存在");
            throw new SellerAuthorizeException();
        }

        String tokenvalue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if (tokenvalue==null){
            log.warn("查询TOKEN失败，TOKEN不存在");
            throw new SellerAuthorizeException();
        }
    }
}
