package com.alibaba.cloud.sentinel.feign;

import com.alibaba.csp.sentinel.SphU;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * <p>
 * 服务熔断配置类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 9:40
 */
@Configuration
public class FunFeignFallbackConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnClass({ SphU.class, Feign.class })
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	@Primary
	public Feign.Builder feignSentinelBuilder() {
		return FunSentinelFeign.builder();
	}

}
