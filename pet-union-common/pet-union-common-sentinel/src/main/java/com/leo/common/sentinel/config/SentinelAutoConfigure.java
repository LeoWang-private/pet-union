package com.leo.common.sentinel.config;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.leo.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Sentinel 配置类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 9:51
 */
@Slf4j
public class SentinelAutoConfigure {

	private SentinelAutoConfigure() {
	}

	/**
	 * 限流、熔断统一处理类
	 */
	@Configuration
	@ConditionalOnClass(HttpServletRequest.class)
	public static class WebmvcHandler {

		@Bean
		public BlockExceptionHandler blockExceptionHandler() {
			return (request, response, e) -> {
				log.error("Sentinel 降级 资源名称{}", e.getRule().getResource(), e);
				response.setContentType(ContentType.JSON.toString());
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				response.getWriter().print(JSONUtil.toJsonStr(Result.failed(e.getMessage())));
			};
		}

	}

	/**
	 * 限流、熔断统一处理类
	 */
	@Configuration
	@ConditionalOnClass(ServerResponse.class)
	public static class WebFluxHandler {

		@Bean
		@SuppressWarnings("unchecked")
		public BlockRequestHandler blockRequestHandler() {
			return (serverWebExchange,
					throwable) -> (Mono<org.springframework.web.reactive.function.server.ServerResponse>) ServerResponse
							.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON)
							.body(BodyInserters.fromObject(Result.failed(throwable.getMessage())));
		}

	}

}
