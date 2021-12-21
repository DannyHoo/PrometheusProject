package com.dannny.monitor.prometheus.jvm.controller;

import com.dannny.monitor.prometheus.jvm.model.User;
import com.dannny.monitor.prometheus.jvm.util.RandomValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UserControllerTest {

    static final ExecutorService threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    @SneakyThrows
    public static void main(String[] args) {
        int count = 0;

        while (true) {
            Thread.sleep(new Random().nextInt(10));
            count++;
            if (count % 2 == 1) {
                log.info("getUser请求 count:{}", count);
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        RestTemplate restTemplate = new RestTemplate();
                        String result = restTemplate.getForObject("http://localhost:6001/user/getUser?userName=" + RandomValue.getTel(), String.class);
                        System.out.println(result);
                    }
                });
            } else {
                log.info("register请求 count:{}", count);
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        RestTemplate restTemplate = new RestTemplate();
                        User request = new User();
                        request.setName(RandomValue.getChineseName());
                        request.setMobile(RandomValue.getTel());
                        request.setBirthday(LocalDate.now());
                        request.setAddress(RandomValue.getRoad());
                        String result = restTemplate.postForObject("http://localhost:6001/user/register", request, String.class);
                        System.out.println(result);
                    }
                });
            }
        }

    }

}
