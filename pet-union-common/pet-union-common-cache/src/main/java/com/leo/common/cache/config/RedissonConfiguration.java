package com.leo.common.cache.config;

import com.leo.common.cache.aop.LockAop;
import com.leo.common.cache.aop.MqAop;
import com.leo.common.cache.operation.RedissonBinary;
import com.leo.common.cache.operation.RedissonCollection;
import com.leo.common.cache.operation.RedissonObject;
import com.leo.common.cache.properties.MultipleServerProperties;
import com.leo.common.cache.properties.RedissonProperties;
import com.leo.common.cache.properties.SingleServerProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.*;
import org.redisson.connection.balancer.LoadBalancer;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Redisson 配置类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 14:34
 */
@Configuration
@EnableConfigurationProperties(value = RedissonProperties.class)
@ConditionalOnClass(RedissonProperties.class)
@AutoConfigureBefore(RedissonAutoConfiguration.class)
public class RedissonConfiguration {
    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean(LockAop.class)
    public LockAop lockAop() {
        return new LockAop();
    }

    @Bean
    @ConditionalOnMissingBean(MqAop.class)
    public MqAop mqAop() {
        return new MqAop();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary RedissonBinary() {
        return new RedissonBinary();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject RedissonObject() {
        return new RedissonObject();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection RedissonCollection() {
        return new RedissonCollection();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        try {
            config.setCodec((Codec) Class.forName(redissonProperties.getCodec()).newInstance());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        config.setTransportMode(redissonProperties.getTransportMode());
        if (redissonProperties.getThreads() != null) {
            config.setThreads(redissonProperties.getThreads());
        }
        if (redissonProperties.getNettyThreads() != null) {
            config.setNettyThreads(redissonProperties.getNettyThreads());
        }
        config.setReferenceEnabled(redissonProperties.getReferenceEnabled());
        config.setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout());
        config.setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        config.setDecodeInExecutor(redissonProperties.getDecodeInExecutor());
        config.setUseScriptCache(redissonProperties.getUseScriptCache());
        config.setMinCleanUpDelay(redissonProperties.getMinCleanUpDelay());
        config.setMaxCleanUpDelay(redissonProperties.getMaxCleanUpDelay());

        MultipleServerProperties multipleServerProperties = redissonProperties.getMultipleServerProperties();

        switch (redissonProperties.getModel()) {
            case SINGLE:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                SingleServerProperties param = redissonProperties.getSingleServerProperties();
                singleServerConfig.setAddress(prefixAddress(param.getAddress()));
                singleServerConfig.setConnectionMinimumIdleSize(param.getConnectionMinimumIdleSize());
                singleServerConfig.setConnectionPoolSize(param.getConnectionPoolSize());
                singleServerConfig.setDatabase(param.getDatabase());
                singleServerConfig.setDnsMonitoringInterval(param.getDnsMonitoringInterval());
                singleServerConfig
                        .setSubscriptionConnectionMinimumIdleSize(param.getSubscriptionConnectionMinimumIdleSize());
                singleServerConfig.setSubscriptionConnectionPoolSize(param.getSubscriptionConnectionPoolSize());
                singleServerConfig.setClientName(redissonProperties.getClientName());
                singleServerConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                singleServerConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                singleServerConfig.setKeepAlive(redissonProperties.getKeepAlive());
                singleServerConfig.setPassword(redissonProperties.getPassword());
                singleServerConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                singleServerConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                singleServerConfig.setRetryInterval(redissonProperties.getRetryInterval());
                singleServerConfig
                        .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                singleServerConfig.setSslKeystore(redissonProperties.getSslKeystore());
                singleServerConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                singleServerConfig.setSslProvider(redissonProperties.getSslProvider());
                singleServerConfig.setSslTruststore(redissonProperties.getSslTruststore());
                singleServerConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                singleServerConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                singleServerConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                singleServerConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case CLUSTER:
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                clusterServersConfig.setScanInterval(multipleServerProperties.getScanInterval());
                clusterServersConfig
                        .setSlaveConnectionMinimumIdleSize(multipleServerProperties.getSlaveConnectionMinimumIdleSize());
                clusterServersConfig.setSlaveConnectionPoolSize(multipleServerProperties.getSlaveConnectionPoolSize());
                clusterServersConfig
                        .setFailedSlaveReconnectionInterval(multipleServerProperties.getFailedSlaveReconnectionInterval());
                clusterServersConfig.setFailedSlaveCheckInterval(multipleServerProperties.getFailedSlaveCheckInterval());
                clusterServersConfig
                        .setMasterConnectionMinimumIdleSize(multipleServerProperties.getMasterConnectionMinimumIdleSize());
                clusterServersConfig.setMasterConnectionPoolSize(multipleServerProperties.getMasterConnectionPoolSize());
                clusterServersConfig.setReadMode(multipleServerProperties.getReadMode());
                clusterServersConfig.setSubscriptionMode(multipleServerProperties.getSubscriptionMode());
                clusterServersConfig.setSubscriptionConnectionMinimumIdleSize(
                        multipleServerProperties.getSubscriptionConnectionMinimumIdleSize());
                clusterServersConfig
                        .setSubscriptionConnectionPoolSize(multipleServerProperties.getSubscriptionConnectionPoolSize());
                clusterServersConfig.setDnsMonitoringInterval(multipleServerProperties.getDnsMonitoringInterval());
                try {
                    clusterServersConfig.setLoadBalancer(
                            (LoadBalancer) Class.forName(multipleServerProperties.getLoadBalancer()).newInstance());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerProperties.getNodeAddresses()) {
                    clusterServersConfig.addNodeAddress(prefixAddress(nodeAddress));
                }
                clusterServersConfig.setClientName(redissonProperties.getClientName());
                clusterServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                clusterServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                clusterServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                clusterServersConfig.setPassword(redissonProperties.getPassword());
                clusterServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                clusterServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                clusterServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                clusterServersConfig
                        .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                clusterServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                clusterServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                clusterServersConfig.setSslProvider(redissonProperties.getSslProvider());
                clusterServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                clusterServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                clusterServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                clusterServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                clusterServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case SENTINEL:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                sentinelServersConfig.setDatabase(multipleServerProperties.getDatabase());
                sentinelServersConfig.setMasterName(multipleServerProperties.getMasterName());
                sentinelServersConfig.setScanInterval(multipleServerProperties.getScanInterval());
                sentinelServersConfig
                        .setSlaveConnectionMinimumIdleSize(multipleServerProperties.getSlaveConnectionMinimumIdleSize());
                sentinelServersConfig.setSlaveConnectionPoolSize(multipleServerProperties.getSlaveConnectionPoolSize());
                sentinelServersConfig
                        .setFailedSlaveReconnectionInterval(multipleServerProperties.getFailedSlaveReconnectionInterval());
                sentinelServersConfig.setFailedSlaveCheckInterval(multipleServerProperties.getFailedSlaveCheckInterval());
                sentinelServersConfig
                        .setMasterConnectionMinimumIdleSize(multipleServerProperties.getMasterConnectionMinimumIdleSize());
                sentinelServersConfig.setMasterConnectionPoolSize(multipleServerProperties.getMasterConnectionPoolSize());
                sentinelServersConfig.setReadMode(multipleServerProperties.getReadMode());
                sentinelServersConfig.setSubscriptionMode(multipleServerProperties.getSubscriptionMode());
                sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(
                        multipleServerProperties.getSubscriptionConnectionMinimumIdleSize());
                sentinelServersConfig
                        .setSubscriptionConnectionPoolSize(multipleServerProperties.getSubscriptionConnectionPoolSize());
                sentinelServersConfig.setDnsMonitoringInterval(multipleServerProperties.getDnsMonitoringInterval());
                try {
                    sentinelServersConfig.setLoadBalancer(
                            (LoadBalancer) Class.forName(multipleServerProperties.getLoadBalancer()).newInstance());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerProperties.getNodeAddresses()) {
                    sentinelServersConfig.addSentinelAddress(prefixAddress(nodeAddress));
                }
                sentinelServersConfig.setClientName(redissonProperties.getClientName());
                sentinelServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                sentinelServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                sentinelServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                sentinelServersConfig.setPassword(redissonProperties.getPassword());
                sentinelServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                sentinelServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                sentinelServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                sentinelServersConfig
                        .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                sentinelServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                sentinelServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                sentinelServersConfig.setSslProvider(redissonProperties.getSslProvider());
                sentinelServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                sentinelServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                sentinelServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                sentinelServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                sentinelServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case REPLICATED:
                ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
                replicatedServersConfig.setDatabase(multipleServerProperties.getDatabase());
                replicatedServersConfig.setScanInterval(multipleServerProperties.getScanInterval());
                replicatedServersConfig
                        .setSlaveConnectionMinimumIdleSize(multipleServerProperties.getSlaveConnectionMinimumIdleSize());
                replicatedServersConfig.setSlaveConnectionPoolSize(multipleServerProperties.getSlaveConnectionPoolSize());
                replicatedServersConfig
                        .setFailedSlaveReconnectionInterval(multipleServerProperties.getFailedSlaveReconnectionInterval());
                replicatedServersConfig.setFailedSlaveCheckInterval(multipleServerProperties.getFailedSlaveCheckInterval());
                replicatedServersConfig
                        .setMasterConnectionMinimumIdleSize(multipleServerProperties.getMasterConnectionMinimumIdleSize());
                replicatedServersConfig.setMasterConnectionPoolSize(multipleServerProperties.getMasterConnectionPoolSize());
                replicatedServersConfig.setReadMode(multipleServerProperties.getReadMode());
                replicatedServersConfig.setSubscriptionMode(multipleServerProperties.getSubscriptionMode());
                replicatedServersConfig.setSubscriptionConnectionMinimumIdleSize(
                        multipleServerProperties.getSubscriptionConnectionMinimumIdleSize());
                replicatedServersConfig
                        .setSubscriptionConnectionPoolSize(multipleServerProperties.getSubscriptionConnectionPoolSize());
                replicatedServersConfig.setDnsMonitoringInterval(multipleServerProperties.getDnsMonitoringInterval());
                try {
                    replicatedServersConfig.setLoadBalancer(
                            (LoadBalancer) Class.forName(multipleServerProperties.getLoadBalancer()).newInstance());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerProperties.getNodeAddresses()) {
                    replicatedServersConfig.addNodeAddress(prefixAddress(nodeAddress));
                }
                replicatedServersConfig.setClientName(redissonProperties.getClientName());
                replicatedServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                replicatedServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                replicatedServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                replicatedServersConfig.setPassword(redissonProperties.getPassword());
                replicatedServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                replicatedServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                replicatedServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                replicatedServersConfig
                        .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                replicatedServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                replicatedServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                replicatedServersConfig.setSslProvider(redissonProperties.getSslProvider());
                replicatedServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                replicatedServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                replicatedServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                replicatedServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                replicatedServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case MASTER_SLAVE:
                MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
                masterSlaveServersConfig.setDatabase(multipleServerProperties.getDatabase());
                masterSlaveServersConfig
                        .setSlaveConnectionMinimumIdleSize(multipleServerProperties.getSlaveConnectionMinimumIdleSize());
                masterSlaveServersConfig.setSlaveConnectionPoolSize(multipleServerProperties.getSlaveConnectionPoolSize());
                masterSlaveServersConfig
                        .setFailedSlaveReconnectionInterval(multipleServerProperties.getFailedSlaveReconnectionInterval());
                masterSlaveServersConfig.setFailedSlaveCheckInterval(multipleServerProperties.getFailedSlaveCheckInterval());
                masterSlaveServersConfig
                        .setMasterConnectionMinimumIdleSize(multipleServerProperties.getMasterConnectionMinimumIdleSize());
                masterSlaveServersConfig.setMasterConnectionPoolSize(multipleServerProperties.getMasterConnectionPoolSize());
                masterSlaveServersConfig.setReadMode(multipleServerProperties.getReadMode());
                masterSlaveServersConfig.setSubscriptionMode(multipleServerProperties.getSubscriptionMode());
                masterSlaveServersConfig.setSubscriptionConnectionMinimumIdleSize(
                        multipleServerProperties.getSubscriptionConnectionMinimumIdleSize());
                masterSlaveServersConfig
                        .setSubscriptionConnectionPoolSize(multipleServerProperties.getSubscriptionConnectionPoolSize());
                masterSlaveServersConfig.setDnsMonitoringInterval(multipleServerProperties.getDnsMonitoringInterval());
                try {
                    masterSlaveServersConfig.setLoadBalancer(
                            (LoadBalancer) Class.forName(multipleServerProperties.getLoadBalancer()).newInstance());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                int index = 0;
                for (String nodeAddress : multipleServerProperties.getNodeAddresses()) {
                    if (index++ == 0) {
                        masterSlaveServersConfig.setMasterAddress(prefixAddress(nodeAddress));
                    }
                    else {
                        masterSlaveServersConfig.addSlaveAddress(prefixAddress(nodeAddress));
                    }
                }
                masterSlaveServersConfig.setClientName(redissonProperties.getClientName());
                masterSlaveServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                masterSlaveServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                masterSlaveServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                masterSlaveServersConfig.setPassword(redissonProperties.getPassword());
                masterSlaveServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                masterSlaveServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                masterSlaveServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                masterSlaveServersConfig
                        .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                masterSlaveServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                masterSlaveServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                masterSlaveServersConfig.setSslProvider(redissonProperties.getSslProvider());
                masterSlaveServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                masterSlaveServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                masterSlaveServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                masterSlaveServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                masterSlaveServersConfig.setTimeout(redissonProperties.getTimeout());
                break;

        }
        return Redisson.create(config);
    }

    private String prefixAddress(String address) {
        if (!StringUtils.isEmpty(address) && !address.startsWith("redis")) {
            return "redis://" + address;
        }
        return address;
    }
}
