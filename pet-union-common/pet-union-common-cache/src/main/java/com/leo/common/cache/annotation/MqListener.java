package com.leo.common.cache.annotation;

import com.leo.common.cache.enums.MqModel;

import java.lang.annotation.*;

/**
 * <p>
 * Mq 监听器注解
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 14:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqListener {

    /**
     * topic name
     * @return String
     */
    String name();

    /**
     * 匹配模式 <br />
     * PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic <br />
     * PATTERN模糊匹配 如: name="myTopic.*" 那么发送者的topic name 可以是 myTopic.name1
     * myTopic.name2.尾缀不限定
     * @return MqModel
     */
    MqModel model() default MqModel.PRECISE;
}
