package com.dannny.monitor.prometheus.jvm.controller;

import com.alibaba.fastjson.JSON;
import com.dannny.monitor.prometheus.jvm.model.User;
import com.dannny.monitor.prometheus.jvm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public User getUser(@RequestParam(value = "userName",required = false) String userName) {
        log.info("OrderController.getUser request:{}", userName);
        User user = userService.getUser(userName);
        log.info("OrderController.getUser result:{}", JSON.toJSONString(user));
        return user;
    }

    @PostMapping("/register")
    public User register(@RequestBody User userRequest) {
        log.info("OrderController.register request:{}", userRequest);
        User user = userService.register(userRequest);
        log.info("OrderController.register result:{}", JSON.toJSONString(user));
        return user;
    }

}
