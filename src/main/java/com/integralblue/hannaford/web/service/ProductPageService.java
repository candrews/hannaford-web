package com.integralblue.hannaford.web.service;

import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.model.ProductPage;

public interface ProductPageService {
	ProductPage getProductPage(String relativeUrl) throws RestClientException;
}
