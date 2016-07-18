package com.integralblue.hannaford.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.integralblue.hannaford.web.exception.HannafordSystemError;
import com.integralblue.hannaford.web.property.ProxyProperties;
import com.integralblue.hannaford.web.service.ProxyService;

@Service
public class RestTemplateProxyService implements ProxyService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ProxyProperties proxyProperties;

	@Override
	public <T> ResponseEntity<T> getEntity(String relativeUrl, Class<T> clazz) throws RestClientException {
		return restTemplate.getForEntity(proxyProperties.getBaseUrl() + relativeUrl, clazz);
	}

}
