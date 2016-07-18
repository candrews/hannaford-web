package com.integralblue.hannaford.web.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.integralblue.hannaford.web.exception.HannafordSystemError;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.ProxyService;

@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private ProxyService proxyService;

	@Override
	public Document getPage(String relativeUrl) {
    	ResponseEntity<String> responseEntity = proxyService.getEntity(relativeUrl, String.class);
    	
    	Document document = Jsoup.parse(responseEntity.getBody());
    	
    	if(document.title().equals("System Error | Hannaford")){
    		throw new HannafordSystemError();
    	}
    	
    	return document;
	}

}
