package com.danny.monitor.prometheus.business.service;

import com.danny.monitor.prometheus.business.bean.Order;

import java.math.BigDecimal;

public interface OrderService {

    Order queryOrder(String orderNo);

    Order createOrder(BigDecimal orderAmount);

}
