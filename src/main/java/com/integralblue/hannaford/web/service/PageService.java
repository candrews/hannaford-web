package com.integralblue.hannaford.web.service;

import org.jsoup.nodes.Document;
import org.springframework.retry.annotation.Retryable;

import com.integralblue.hannaford.web.exception.HannafordSystemError;

public interface PageService {

	@Retryable(HannafordSystemError.class)
	Document getPage(String relativeUrl);
}
