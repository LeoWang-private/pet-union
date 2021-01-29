package com.leo.common.cache.config;

import com.leo.common.cache.mq.RedissonMqListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MQ 配置类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 13:45
 */
@Configuration
public class MqConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedissonMqListener.class)
    public RedissonMqListener redissonMqListener() {
        return new RedissonMqListener();
    }
}
