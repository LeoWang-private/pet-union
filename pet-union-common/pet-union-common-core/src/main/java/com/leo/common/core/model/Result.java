package com.leo.common.core.model;

import com.leo.common.core.enums.ExceptionCodeEnum;
import com.leo.common.core.enums.ResultEnum;
import com.leo.common.core.util.MessageUtils;
import com.leo.common.core.util.SpringContextHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 响应信息类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 14:19
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回标记：成功标记=200，失败标记非200")
	private int code;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回信息")
	private String msg;

	@Getter
	@Setter
	@ApiModelProperty(value = "数据")
	private T data;

	public static <T> Result<T> ok() {
		return restResult(null, ResultEnum.SUCCESS.getCode(), null);
	}

	public static <T> Result<T> ok(T data) {
		return restResult(data, ResultEnum.SUCCESS.getCode(), null);
	}

	public static <T> Result<T> ok(T data, String msg) {
		String s = SpringContextHolder.getBean(MessageUtils.class).get(msg);
		return restResult(data, ResultEnum.SUCCESS.getCode(), s);
	}

	public static <T> Result<T> failed() {
		return restResult(null, ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
	}

	public static <T> Result<T> failed(String msg) {
		String s = SpringContextHolder.getBean(MessageUtils.class).get(msg);
		return restResult(null, ResultEnum.FAIL.getCode(), s);
	}

	public static <T> Result<T> failed(T data) {
		return restResult(data, ResultEnum.FAIL.getCode(), null);
	}

	public static <T> Result<T> failed(T data, String msg) {
		String s = SpringContextHolder.getBean(MessageUtils.class).get(msg);
		return restResult(data, ResultEnum.FAIL.getCode(), s);
	}

	public static <T> Result<T> failed(int code, String msg) {
		String s = SpringContextHolder.getBean(MessageUtils.class).get(msg);
		return restResult(null, code, s);
	}

	public boolean success() {
		return this.code == ResultEnum.SUCCESS.getCode();
	}

	public static <T> Result<T> error(ExceptionCodeEnum exceptionCodeEnum) {
		// 国际化支持
		String s = SpringContextHolder.getBean(MessageUtils.class).get(exceptionCodeEnum.getMsg());
		return restResult(null, exceptionCodeEnum.getCode(), s);
	}

	private static <T> Result<T> restResult(T data, int code, String msg) {
		Result<T> apiResult = new Result<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

}
