package com.danny.monitor.prometheus.project.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private String mobile;
    private LocalDate birthday;
    private String address;
}
