package com.hlx.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key,String value){
        if (redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }

        String currentvalue=redisTemplate.opsForValue().get(key);
//        如果锁过期
        if (!StringUtils.isEmpty(currentvalue) && Long.parseLong(currentvalue) < System.currentTimeMillis()){

//            获取上一时间段的value
            String oldvalue=redisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldvalue)&&oldvalue.equals(currentvalue)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */

     public void onlock(String key,String value){
        try {
            String currentvalue=redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentvalue)&&currentvalue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.info("[Redis分布式锁]，解锁失败，{}",e);
        }
     }
}
