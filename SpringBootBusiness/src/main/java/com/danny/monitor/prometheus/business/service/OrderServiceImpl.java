package com.danny.monitor.prometheus.business.service;

import com.alibaba.fastjson.JSON;
import com.danny.monitor.prometheus.business.bean.Order;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MeterRegistry registry;

    private Counter queryOrderCounter; // 累计查询次数
    private Counter orderSuccessCounter; // 累计成功订单数量
    private Counter orderFailedCounter; // 累计失败订单数量
    private DistributionSummary orderSuccessSummary; // 成功订单金额
    private DistributionSummary orderFailedSummary; // 失败订单金额

    @PostConstruct
    private void init() {
        queryOrderCounter = registry.counter("query_order_total", "query_order_total", "success");
        orderSuccessCounter = registry.counter("create_order_total", "create_order_total", "success");
        orderFailedCounter = registry.counter("create_order_total", "create_order_total", "failed");
        orderSuccessSummary = registry.summary("order_amount", "order_amount", "success");
        orderFailedSummary = registry.summary("order_amount", "order_amount", "failed");

    }

    // 查询订单
    @Override
    public Order queryOrder(String orderNo) {
        log.info("queryOrder orderNo:{}", orderNo);
        queryOrderCounter.increment(); // prometheus埋点数据
        Order order = new Order();
        order.setOrderAmount(BigDecimal.valueOf(RandomUtils.nextInt()));
        order.setOrderNo(orderNo);
        order.setOrderTime(LocalDateTime.now());
        log.info("queryOrder result:{}", JSON.toJSONString(order));
        return order;
    }

    // 创建订单
    @Override
    public Order createOrder(BigDecimal orderAmount) {
        log.info("createOrder orderAmount:{}", orderAmount);
        Order order = null;
        try {
            if (orderAmount.compareTo(BigDecimal.valueOf(10)) < 0) {
                throw new Exception("金额小于10时，模拟订单失败的情况");
            }
            order = new Order();
            order.setOrderAmount(orderAmount);
            order.setOrderNo(RandomStringUtils.randomAlphabetic(32));
            order.setOrderTime(LocalDateTime.now());
            orderSuccessCounter.increment(); // prometheus埋点数据
            orderSuccessSummary.record(orderAmount.doubleValue()); // prometheus埋点数据
        } catch (Exception e) {
            log.info("createOrder error", e);
            orderFailedCounter.increment();  // prometheus埋点数据
            orderFailedSummary.record(orderAmount.doubleValue());  // prometheus埋点数据
            order = null;
        } finally {
            log.info("queryOrder result:{}", JSON.toJSONString(order));
            return order;
        }
    }

}
