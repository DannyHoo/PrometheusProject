package com.danny.monitor.prometheus.business.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Order {
    private String orderNo;
    private BigDecimal orderAmount;
    private LocalDateTime orderTime;
}
