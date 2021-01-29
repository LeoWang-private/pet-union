package com.leo.common.cache.annotation;

import com.leo.common.cache.config.CacheConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 开启缓存
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 11:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import(CacheConfiguration.class)
@Configuration
public @interface EnableCache {
    /**
     * 缓存的名称 @Cacheable,@CachePut,@CacheEvict的value必须包含在这里面
     * @return String[]
     */
    String[] value();

    /**
     * 缓存时间 默认30分钟
     * @return long
     */
    long ttl() default 1000 * 60 * 30L;

    /**
     * 最长空闲时间 默认30分钟
     * @return long
     */
    long maxIdleTime() default 1000 * 60 * 30L;
}
