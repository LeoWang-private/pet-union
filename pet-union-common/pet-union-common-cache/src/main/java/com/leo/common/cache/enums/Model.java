package com.leo.common.cache.enums;

/**
 * <p>
 * Redis 模式枚举类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 14:46
 */
public enum Model {

    /**
     * 哨兵
     */
    SENTINEL,
    /**
     * 主从
     */
    MASTER_SLAVE,
    /**
     * 单例
     */
    SINGLE,
    /**
     * 集群
     */
    CLUSTER,
    /**
     * 云托管模式
     */
    REPLICATED
}
