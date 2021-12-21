package com.dannny.monitor.prometheus.jvm.service;


import com.dannny.monitor.prometheus.jvm.model.User;

public interface UserService {

    User getUser(String userName);

    User register(User userRequest);
}
