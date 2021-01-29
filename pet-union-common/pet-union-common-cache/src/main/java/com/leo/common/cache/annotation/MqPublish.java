package com.leo.common.cache.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * Mq 发布消息注解
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 15:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqPublish {
    /**
     * topic name
     * @return String
     */
    String name();
}
