package com.danny.monitor.prometheus.business.controller;

import com.danny.monitor.prometheus.business.bean.Order;
import com.danny.monitor.prometheus.business.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OrderControllerTest {

    static final ExecutorService threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    @SneakyThrows
    public static void main(String[] args) {
        randomRequestTest();
    }


    public static void queryOrderTest(){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:6002/order/queryOrder?orderNo=" + RandomStringUtils.random(32), String.class);
        System.out.println(result);
    }

    public static void createOrderTest(){
        RestTemplate restTemplate = new RestTemplate();
        Order orderRequest=new Order();
        orderRequest.setOrderAmount(BigDecimal.valueOf(9));
        String result = restTemplate.postForObject("http://localhost:6002/order/createOrder", orderRequest, String.class);
        System.out.println(result);
    }

    @SneakyThrows
    public static void randomRequestTest(){
        int count = 0;

        while (true) {
            int randomCount=new Random().nextInt(1000);
            int randomWaitTime=new Random().nextInt(1200);
            log.info("randomCount randomWaitTime :{} {}",randomCount, randomWaitTime);
            while (randomCount>0){
                randomCount--;
                Thread.sleep(randomWaitTime);
                count++; // 用于模拟分担接口流量
                if (count % 2 == 1) {
                    log.info("getUser请求 count:{}", count);
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            RestTemplate restTemplate = new RestTemplate();
                            String result = restTemplate.getForObject("http://localhost:6002/order/queryOrder?orderNo=" + RandomStringUtils.random(32), String.class);
                            System.out.println(result);
                        }
                    });
                } else {
                    log.info("register请求 count:{}", count);
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            RestTemplate restTemplate = new RestTemplate();
                            Order orderRequest=new Order();
                            orderRequest.setOrderAmount(BigDecimal.valueOf(RandomUtils.nextInt(100)));
                            String result = restTemplate.postForObject("http://localhost:6002/order/createOrder", orderRequest, String.class);
                            System.out.println(result);
                        }
                    });
                }

            }

        }
    }
}
