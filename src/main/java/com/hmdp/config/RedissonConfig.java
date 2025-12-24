package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedissonClient redissonClient() {
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword);
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

/*    @Bean
    public RedissonClient redissonClient2() {
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":6380");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

    @Bean
    public RedissonClient redissonClient3() {
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":6381" );
        // 创建RedissonClient对象
        return Redisson.create(config);
    }*/
}