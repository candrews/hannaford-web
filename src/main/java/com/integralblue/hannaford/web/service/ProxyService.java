package com.integralblue.hannaford.web.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public interface ProxyService {
	<T> ResponseEntity<T> getEntity(String relativeUrl, Class<T> clazz) throws RestClientException;
}
