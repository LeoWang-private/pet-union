package com.leo.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * <p>
 * 国际化工具类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-27 14:34
 */
@Slf4j
public class MessageUtils {

	private final MessageSource messageSource;

	public MessageUtils(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 获取单个国际化翻译值
	 */
	public String get(String msgKey) {
		try {
			return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("国际化异常：{}", e.getLocalizedMessage());
			return msgKey;
		}
	}

	/**
	 * 获取单个国际化翻译值
	 */
	public String get(String msgKey, Object[] args) {
		try {
			return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("国际化异常：{}", e.getLocalizedMessage());
			return msgKey;
		}
	}

}
