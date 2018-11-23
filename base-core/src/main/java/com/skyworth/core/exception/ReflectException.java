package com.skyworth.core.exception;

public class ReflectException extends BaseRuntimeException{

	private static final long serialVersionUID = 6201618364025814077L;

	public ReflectException() {
		super();
	}

	public ReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectException(String message) {
		super(message);
	}

	public ReflectException(Throwable cause) {
		super(cause);
	}
}
