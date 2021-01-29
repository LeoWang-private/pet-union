package com.leo.common.cache.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 开启MQ
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 13:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface EnableMq {
}
