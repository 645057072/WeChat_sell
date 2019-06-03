package com.hlx.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    public static final Logger LOGGER= LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void loggerTest(){
        LOGGER.debug("debug。。。。。");
        LOGGER.info("info........");
        LOGGER.error("error。。。。。");
    }
}
