package com.hlx.sell.service.Impl;

import com.hlx.sell.exception.SellException;
import com.hlx.sell.service.RedisLock;
import com.hlx.sell.service.SkillService;
import com.hlx.sell.utils.KeyUitls;
import com.lly835.bestpay.rest.type.Get;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Service
public class SkillServiceImpl implements SkillService {
    private final static int TIMEOUT=10*100;//时间10s
    @Autowired
    private RedisLock redisLock;

  static Map<String,Integer> products;
  static Map<String,Integer> stock;
  static Map<String,String> orders;
  static
  {
      products=new HashMap<>();
      stock=new HashMap<>();
      orders=new HashMap<>();
      products.put("12345",10000);
      stock.put("12345",10000);
  }


    private String queryMap(String productId) {
        return "618活动大促销，美体连衣裙，限量"+products.get(productId)+"还剩"+stock.get(productId)+"该商品下单人数"+orders.size()+"人";
    }
   @Override
   public String querySeckillProductInfo(String productId){
            return this.queryMap(productId);
   }
    @Override
    public  void orderProductMockDiffUser(String productId) {
      long time=System.currentTimeMillis()+ TIMEOUT;
      if (!redisLock.lock(productId,String.valueOf(time))){
          throw new SellException(119,"哎呦喂，排队人数太多了，换个姿势试试~~~");
        }
      int stockNum=stock.get(productId);
      if (stockNum==0){
          throw  new SellException(100,"活动结束");
      }else {
        orders.put(KeyUitls.getqUniqueKey(),productId);
        stockNum=stockNum-1;
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        stock.put(productId,stockNum);
      }
      redisLock.onlock(productId,String.valueOf(time));
    }
}
