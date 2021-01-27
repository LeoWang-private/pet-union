package com.alibaba.cloud.sentinel.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.common.core.enums.ExceptionCodeEnum;
import com.leo.common.core.model.Result;
import com.leo.common.core.util.MessageUtils;
import com.leo.common.core.util.SpringContextHolder;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 * Fallback处理器
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 14:05
 */
@Slf4j
@AllArgsConstructor
public class FunFeignFallback<T> implements MethodInterceptor {
    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    @Override
    @Nullable
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class<?> returnType = method.getReturnType();
        String errorMessage = cause.getMessage();
        log.error("FunFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(),
                targetName, errorMessage);
        if (Result.class != returnType) {
            return null;
        }

        String str;
        if (cause instanceof FeignException) {
            FeignException exception = (FeignException) cause;
            str = exception.contentUTF8();
            ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
            return objectMapper.readValue(str, Result.class);
        } else {
            log.error("feign异常:{}", cause.getMessage());
            Result<Void> result = new Result<>();
            result.setCode(ExceptionCodeEnum.SERVICE_UNAVAILABLE.getCode());
            String s = SpringContextHolder.getBean(MessageUtils.class)
                    .get(ExceptionCodeEnum.SERVICE_UNAVAILABLE.getMsg());
            s = String.format(s, targetName);
            result.setMsg(s);
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FunFeignFallback<?> that = (FunFeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }
}
