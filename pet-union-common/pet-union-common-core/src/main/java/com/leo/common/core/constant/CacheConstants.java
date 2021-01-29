package com.leo.common.core.constant;

/**
 * <p>
 * 缓存常量
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-29 11:14
 */
public class CacheConstants {
    private CacheConstants() {}

    /**
     * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
     * <p/>
     * {@code @Cacheable(value = CacheConstants.GLOBALLY+CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")}
     */
    public static final String GLOBALLY = "gl:";

    /**
     * 验证码前缀
     */
    public static final String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

    /**
     * 菜单信息缓存
     */
    public static final String MENU_DETAILS = "menu_details";

    /**
     * 用户信息缓存
     */
    public static final String USER_DETAILS = "user_details";

    /**
     * 字典信息缓存
     */
    public static final String DICT_DETAILS = "dict_details";

    /**
     * oauth 客户端信息
     */
    public static final String CLIENT_DETAILS_KEY = "bysk_oauth:client:details";

    /**
     * 路由存放
     */
    public static final String ROUTE_KEY = "gateway_route_key";

    /**
     * redis reload 事件
     */
    public static final String ROUTE_REDIS_RELOAD_TOPIC = "gateway_redis_route_reload_topic";

    /**
     * 内存reload 时间
     */
    public static final String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";

    /**
     * 租户缓存
     */
    public static final String TENANT_DETAILS = "tenant_details";

}
