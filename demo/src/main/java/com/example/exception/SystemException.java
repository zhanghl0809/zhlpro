/*
 * Copyright 2014-2018 buyforyou.cn.
 * All rights reserved.
 */
package com.example.exception;

/**
 * 系统异常
 * 
 * @author zhl
 * @since 1.8
 */
public class SystemException extends RuntimeException {

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
