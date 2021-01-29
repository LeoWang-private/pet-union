package com.leo.common.cache.config;

import com.leo.common.cache.annotation.EnableCache;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 缓存 配置类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 11:59
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(value = RedissonProperties.class)
@Slf4j
public class CacheConfiguration implements ImportAware {
    private String[] value;

    /**
     * 缓存时间 默认30分钟
     */
    private long ttl;

    /**
     * 最长空闲时间 默认30分钟
     */
    private long maxIdleTime;

    @Resource
    private RedissonClient redissonClient;

    @Bean
    CacheManager cacheManager() {
        Map<String, CacheConfig> config = new HashMap<>(1);
        // 创建一个名称为"testMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
        for (String s : value) {
            log.info("初始化spring cache空间{}", s);
            config.put(s, new CacheConfig(ttl, maxIdleTime));
        }
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> enableAttrMap = importMetadata.getAnnotationAttributes(EnableCache.class.getName());
        AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(enableAttrMap);
        assert enableAttrs != null;
        this.value = enableAttrs.getStringArray("value");
        this.maxIdleTime = enableAttrs.getNumber("maxIdleTime");
        this.ttl = enableAttrs.getNumber("ttl");
    }
}
