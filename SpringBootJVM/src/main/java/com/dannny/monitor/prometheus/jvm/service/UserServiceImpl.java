package com.dannny.monitor.prometheus.jvm.service;

import com.alibaba.fastjson.JSON;
import com.dannny.monitor.prometheus.jvm.model.User;
import com.dannny.monitor.prometheus.jvm.util.RandomValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String userName) {
        log.info("UserServiceImpl.getUser request:{}", userName);
        User user = new User();
        user.setId(1L);
        user.setName(userName);
        user.setMobile(RandomValue.getTel());
        user.setBirthday(LocalDate.now());
        user.setAddress(RandomValue.getRoad());
        log.info("UserServiceImpl.getUser result:{}", user);
        return user;
    }

    @Override
    public User register(User userRequest) {
        log.info("UserServiceImpl.register request:{}", JSON.toJSONString(userRequest));
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        user.setId(new Random().nextLong());
        log.info("UserServiceImpl.register result:{}", user);
        return user;
    }

}
