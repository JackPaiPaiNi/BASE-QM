package com.skyworth.core.exception;

public class DaoException extends BaseRuntimeException {

	private static final long serialVersionUID = -6879786814200387154L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
