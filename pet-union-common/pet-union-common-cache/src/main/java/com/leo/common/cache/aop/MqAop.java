package com.leo.common.cache.aop;

import com.leo.common.cache.annotation.MqPublish;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Mq 切面
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 17:03
 */
@Aspect
@Slf4j
public class MqAop {
    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(mq)")
    public void aspect(MqPublish mq) {
    }

    @Around("aspect(mq)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, MqPublish mq) {
        try {
            Object obj = proceedingJoinPoint.proceed();
            RTopic topic = redissonClient.getTopic(mq.name());
            topic.publish(obj);
            return obj;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}
