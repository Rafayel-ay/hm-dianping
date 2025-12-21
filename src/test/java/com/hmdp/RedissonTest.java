package com.hmdp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class RedissonTest {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedissonClient redissonClient2;
    @Resource
    private RedissonClient redissonClient3;

    private RLock lock;

    @BeforeEach
    void setUp() {
        RLock lock1 = redissonClient.getLock("lock");
        RLock lock2 = redissonClient2.getLock("lock");
        RLock lock3 = redissonClient3.getLock("lock");
        // 使用联合锁 multiLock
        lock = redissonClient.getMultiLock(lock1, lock2, lock3);
    }

    @Test
    void method1() {
        boolean success = lock.tryLock();
        redissonClient.getMultiLock();
        if (!success) {
            log.error("获取锁失败，1");
            return;
        }
        try {
            log.info("获取锁成功");
            method2();
        } finally {
            log.info("释放锁，1");
            lock.unlock();
        }
    }

    void method2() {
        RLock lock = redissonClient.getLock("lock");
        boolean success = lock.tryLock();
        if (!success) {
            log.error("获取锁失败，2");
            return;
        }
        try {
            log.info("获取锁成功，2");
        } finally {
            log.info("释放锁，2");
            lock.unlock();
        }
    }
}
