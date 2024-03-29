package com.atomscat.spring.boot.common.aop.aspect;

import cn.hutool.core.util.StrUtil;
import com.atomscat.spring.boot.common.aop.annotation.RedisLock;
import com.atomscat.spring.boot.common.util.SpelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 使用redis进行分布式锁
 *
 * @author th158
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisLockAspect {

    private static final String REDISSON_LOCK_PREFIX = "redisson_lock:";

    private final RedissonClient redissonClient;

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        String spel = redisLock.key();
        String lockName = redisLock.lockName();
        String key = getRedisKey(joinPoint, lockName, spel);
        RLock rLock = redissonClient.getLock(key);

        Object result = null;

        try {
            if (rLock.tryLock(redisLock.waitTime(), redisLock.expire(), redisLock.timeUnit())) {
                log.info("lock key: {}", key);
                //执行方法
                result = joinPoint.proceed();
            } else {
                log.info("is locked key: {}", key);
            }
        } catch (Throwable e) {
            log.error("redis lock aspect error", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 将spel表达式转换为字符串
     *
     * @param joinPoint 切点
     * @return redisKey
     */
    private String getRedisKey(ProceedingJoinPoint joinPoint, String lockName, String spel) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        return REDISSON_LOCK_PREFIX + lockName + StrUtil.COLON + SpelUtil.parse(target, spel, targetMethod, arguments);
    }
}
