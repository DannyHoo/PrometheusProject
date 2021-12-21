package com.danny.monitor.prometheus.project.service;

import com.danny.monitor.prometheus.project.model.User;

public interface UserService {

    User getUser(String userName);

    User register(User userRequest);
}
