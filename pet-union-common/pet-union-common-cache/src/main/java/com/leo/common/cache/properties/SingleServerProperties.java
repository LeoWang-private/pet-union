package com.leo.common.cache.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 单节点配置的属性
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 14:55
 */
@Getter
@Setter
public class SingleServerProperties {

    private String address;

    private Integer subscriptionConnectionMinimumIdleSize = 1;

    private Integer subscriptionConnectionPoolSize = 50;

    private Integer connectionMinimumIdleSize = 32;

    private Integer connectionPoolSize = 64;

    private Integer database = 0;

    private Long dnsMonitoringInterval = 5000L;

}
