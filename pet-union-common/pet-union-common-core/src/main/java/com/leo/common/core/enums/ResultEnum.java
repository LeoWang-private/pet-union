package com.leo.common.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 通用的结果枚举，服务若要返回自己的结果信息，需要定义国际化文件和key的常量
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 14:23
 */
public enum ResultEnum {
    /**
     * 操作成功： 2XX系列
     */
    SUCCESS(200, "success"),

    /**
     * 操作失败
     */
    FAIL(1, "fail"),

    ;

    /***
     * 状态码
     */
    @Getter
    @Setter
    private int code;

    /***
     * 状态码标识符
     */
    @Getter
    @Setter
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultEnum valueOf(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.getCode() == code) {
                return resultEnum;
            }
        }
        return FAIL;
    }
}
