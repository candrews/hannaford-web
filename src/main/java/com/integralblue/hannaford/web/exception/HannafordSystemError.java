package com.integralblue.hannaford.web.exception;

public class HannafordSystemError extends RuntimeException {

	public HannafordSystemError() {
		super();
	}

	public HannafordSystemError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HannafordSystemError(String message, Throwable cause) {
		super(message, cause);
	}

	public HannafordSystemError(String message) {
		super(message);
	}

	public HannafordSystemError(Throwable cause) {
		super(cause);
	}

}
