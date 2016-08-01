package com.integralblue.hannaford.web.exception;

public class StoreRequiredException extends RuntimeException {

	public StoreRequiredException() {
		super();
	}

	public StoreRequiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StoreRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoreRequiredException(String message) {
		super(message);
	}

	public StoreRequiredException(Throwable cause) {
		super(cause);
	}

}
