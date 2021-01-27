package com.leo.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 错误code 定义 服务名_操作_类型错误
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 14:36
 */
@Getter
@AllArgsConstructor
public enum ExceptionCodeEnum {
    /**
     * 业务异常： 4XX系列
     */
    BIZ_BAD_REQUEST(400, "BAD_REQUEST"),
    BIZ_NOT_FOUND(401, "NOT_FOUND"),
    BIZ_FORBIDDEN(403, "FORBIDDEN"),
    BIZ_CONFLICT(409, "CONFLICT"),
    /**
     * 主机异常：5XX系列
     */
    SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
    /*
     * 90**** 开头 auth 错误
     */
    AUTH_EXCEPTION(90000, "auth.exception"),
    AUTH_FORBIDDEN_EXCEPTION(90001, "auth.access.denied"),
    AUTH_UNAUTHORIZED(90002,"auth.unauthorized"),
    AUTH_INVALID_EXCEPTION(90003, "invalid.access.token"),
    AUTH_METHOD_NOT_ALLOWED(90004, "auth.server.error"),
    AUTH_BAD_CREDENTIALS_EXCEPTION(90006, "auth.bad.credentials.exception"),
    AUTH_CREDENTIAL_EXPIRED(900007, "AccountStatusUserDetailsChecker.credentialsExpired"),
    AUTH_USER_NAME_NOT_FOUND(90008, "DigestAuthenticationFilter.usernameNotFound"),
    AUTH_USER_LOCKED(90009, "AccountStatusUserDetailsChecker.locked"),
    AUTH_USER_DISABLED(90010, "AccountStatusUserDetailsChecker.disabled"),
    AUTH_USER_EXPIRED(90011, "AccountStatusUserDetailsChecker.expired"),
    AUTH_BAD_PWD(90012, "BindAuthenticator.badCredentials"),
    AUTH_ACCESS_DENIED(900013,"AbstractAccessDecisionManager.accessDenied"),
    ;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;
}
