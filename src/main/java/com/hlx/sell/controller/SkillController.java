package com.hlx.sell.controller;

import com.hlx.sell.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping("/query/{productId}")
    public String querySkill(@PathVariable String productId) throws Exception{
        return skillService.querySeckillProductInfo(productId);
    }

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception{
        log.info("@skill request,productId:"+productId);
        skillService.orderProductMockDiffUser(productId);
        return skillService.querySeckillProductInfo(productId);
    }

}
