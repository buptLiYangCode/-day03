package com.hmdp.utils;



import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements ILock{

    private String name;
    private StringRedisTemplate stringRedisTemplate;
    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String KEY_PREFIX = "lock:";
    // UUID是static的属于类，一个JVM中都是一个值，可以避免不同JVM出现相同线程号
    private static final String ID_PREFIX = UUID.randomUUID().toString().replace("-", "");



    @Override
    public boolean tryLock(long timeoutSec) {
        // 获取线程id
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX+name, threadId, timeoutSec,
                TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unLock() {
        //获取线程标识
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 获取自己持有锁的标识
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        if (id.equals(threadId)) {
            stringRedisTemplate.delete(KEY_PREFIX+name);
        }
    }


}
