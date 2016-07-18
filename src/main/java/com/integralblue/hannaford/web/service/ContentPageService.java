package com.integralblue.hannaford.web.service;

import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.model.ContentPage;

public interface ContentPageService {
	ContentPage getContentPage(String relativeUrl) throws RestClientException;
	ContentPage getHomePage() throws RestClientException;
}
