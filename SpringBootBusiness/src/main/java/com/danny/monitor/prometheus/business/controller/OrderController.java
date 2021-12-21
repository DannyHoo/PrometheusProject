package com.danny.monitor.prometheus.business.controller;

import com.alibaba.fastjson.JSON;
import com.danny.monitor.prometheus.business.bean.Order;
import com.danny.monitor.prometheus.business.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/queryOrder")
    public Order queryOrder(@RequestParam(value = "orderNo",required = false) String orderNo) {
        log.info("OrderController.queryOrder request:{}", orderNo);
        Order order = orderService.queryOrder(orderNo);
        log.info("OrderController.queryOrder result:{}", JSON.toJSONString(order));
        return order;
    }

    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody Order orderRequest) {
        log.info("OrderController.createOrder request:{}", orderRequest);
        Order order = orderService.createOrder(orderRequest.getOrderAmount());
        log.info("OrderController.createOrder result:{}", JSON.toJSONString(order));
        return order;
    }

}
