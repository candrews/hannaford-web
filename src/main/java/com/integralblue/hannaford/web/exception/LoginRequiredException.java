package com.integralblue.hannaford.web.exception;

public class LoginRequiredException extends RuntimeException {

	public LoginRequiredException() {
		super();
	}

	public LoginRequiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginRequiredException(String message) {
		super(message);
	}

	public LoginRequiredException(Throwable cause) {
		super(cause);
	}

}
