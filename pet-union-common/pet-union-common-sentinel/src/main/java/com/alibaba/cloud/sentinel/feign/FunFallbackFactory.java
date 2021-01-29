package com.alibaba.cloud.sentinel.feign;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * <p>
 * 公共FallbackFactory
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 17:16
 */
@AllArgsConstructor
public class FunFallbackFactory<T> implements FallbackFactory<T> {

	private final Target<T> target;

	@Override
	@SuppressWarnings("unchecked")
	public T create(Throwable cause) {
		final Class<T> targetType = target.type();
		final String targetName = target.name();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetType);
		enhancer.setUseCache(true);
		enhancer.setCallback(new FunFeignFallback<>(targetType, targetName, cause));
		return (T) enhancer.create();
	}

}
