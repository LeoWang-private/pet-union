package com.leo.common.cache.mq;

import com.leo.common.cache.annotation.MqListener;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * Mq 监听
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 13:50
 */
@Slf4j
public class RedissonMqListener implements BeanPostProcessor {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            MqListener annotation = AnnotationUtils.findAnnotation(method, MqListener.class);
            if (annotation != null) {
                switch (annotation.model()) {
                    case PRECISE:
                        RTopic topic = redissonClient.getTopic(annotation.name());
                        log.info("注解redisson精准监听器name={},beanName={}", annotation.name(), beanName);
                        topic.addListener(Object.class, (channel, msg) -> {
                            try {
                                Object[] aras = new Object[method.getParameterTypes().length];
                                int index = 0;
                                for (Class<?> parameterType : method.getParameterTypes()) {
                                    String simpleName = parameterType.getSimpleName();
                                    if ("CharSequence".equals(simpleName)) {
                                        aras[index++] = channel;
                                    } else if (msg.getClass().getSimpleName().equals(simpleName)
                                            || "Object".equals(simpleName)) {
                                        aras[index++] = msg;
                                    } else {
                                        aras[index++] = null;
                                    }
                                }
                                method.invoke(bean, aras);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case PATTERN:
                        RPatternTopic patternTopic = redissonClient.getPatternTopic(annotation.name());
                        log.info("注解redisson模糊监听器name={}", annotation.name());
                        patternTopic.addListener(Object.class, (pattern, channel, msg) -> {
                            try {
                                Object[] aras = new Object[method.getParameterTypes().length];
                                int index = 0;
                                boolean patternFlag = false;
                                for (Class<?> parameterType : method.getParameterTypes()) {
                                    String simpleName = parameterType.getSimpleName();
                                    if ("CharSequence".equals(simpleName)) {
                                        if (!patternFlag) {
                                            patternFlag = true;
                                            aras[index++] = pattern;
                                        } else {
                                            aras[index++] = channel;
                                        }
                                    } else if (msg.getClass().getSimpleName().equals(simpleName)
                                            || "Object".equals(simpleName)) {
                                        aras[index++] = msg;
                                    } else {
                                        aras[index++] = null;
                                    }
                                }
                                method.invoke(bean, aras);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    default:
                        throw new RuntimeException("订阅类型失败!");
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }
}
