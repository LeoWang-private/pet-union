package com.leo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 * 认证授权微服务启动类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-26 13:56
 */
@SpringCloudApplication
@EnableFeignClients
@EnableCaching
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
