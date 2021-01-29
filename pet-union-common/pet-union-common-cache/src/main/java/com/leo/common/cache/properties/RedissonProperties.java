package com.leo.common.cache.properties;

import com.leo.common.cache.enums.LockModel;
import com.leo.common.cache.enums.Model;
import lombok.Getter;
import lombok.Setter;
import org.redisson.config.SslProvider;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * <p>
 * RedissonProperties
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 14:37
 */
@ConfigurationProperties(prefix = "redisson")
@Component
@Getter
@Setter
public class RedissonProperties {

    private Model model = Model.SINGLE;

    private String codec = "org.redisson.codec.JsonJacksonCodec";

    private Integer threads;

    private Integer nettyThreads;

    private TransportMode transportMode = TransportMode.NIO;

    /**
     * 公共参数
     */
    private Integer idleConnectionTimeout = 10000;

    private Integer connectTimeout = 10000;

    private Integer timeout = 3000;

    private Integer retryAttempts = 3;

    private Integer retryInterval = 1500;

    private String password;

    private Integer subscriptionsPerConnection = 5;

    private String clientName;

    private Boolean sslEnableEndpointIdentification = true;

    private SslProvider sslProvider = SslProvider.JDK;

    private URL sslTruststore;

    private String sslTruststorePassword;

    private URL sslKeystore;

    private String sslKeystorePassword;

    private Integer pingConnectionInterval = 0;

    private Boolean keepAlive = false;

    private Boolean tcpNoDelay = false;

    private Boolean referenceEnabled = true;

    private Long lockWatchdogTimeout = 30000L;

    private Boolean keepPubSubOrder = true;

    private Boolean decodeInExecutor = false;

    private Boolean useScriptCache = false;

    private Integer minCleanUpDelay = 5;

    private Integer maxCleanUpDelay = 1800;

    /**
     * 锁的模式 如果不设置 单个key默认可重入锁 多个key默认联锁
     */
    private LockModel lockModel;

    /**
     * 等待加锁超时时间 -1一直等待
     */
    private Long attemptTimeout = 10000L;

    /**
     * 数据缓存时间 默认30分钟
     */
    private Long dataValidTime = 1000 * 60 * 30L;

    @NestedConfigurationProperty
    private SingleServerProperties singleServerProperties;

    @NestedConfigurationProperty
    private MultipleServerProperties multipleServerProperties;

}