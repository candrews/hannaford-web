package com.integralblue.hannaford.web.service;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.exception.HannafordSystemError;
import com.integralblue.hannaford.web.exception.LoginException;

public interface LoginService {
	@Retryable(HannafordSystemError.class)
	void login(String username, String password) throws RestClientException,LoginException;
}
