package com.nishioffduty.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("email","nishishah0104@gmail.com");
        Object email= redisTemplate.opsForValue().get("email");
    }
}
