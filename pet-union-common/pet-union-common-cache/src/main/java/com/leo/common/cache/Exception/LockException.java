package com.leo.common.cache.Exception;

/**
 * <p>
 * 自定义分布式锁异常
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 16:56
 */
public class LockException extends RuntimeException {
    public LockException() {
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
