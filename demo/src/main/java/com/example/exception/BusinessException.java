/*
 * Copyright 2014-2018 buyforyou.cn.
 * All rights reserved.
 */
package com.example.exception;

/**
 * 业务异常
 * 
 * @author zhl
 * @since 1.8
 */
public class BusinessException extends SystemException {

	private static final long serialVersionUID = 1L;

	private String code;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(String code, String message, Object... args) {
		super(String.format(code, args));
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
