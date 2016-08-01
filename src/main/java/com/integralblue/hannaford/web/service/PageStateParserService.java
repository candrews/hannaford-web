package com.integralblue.hannaford.web.service;

import org.jsoup.nodes.Document;

import com.integralblue.hannaford.web.model.PageState;

public interface PageStateParserService {
	PageState getPageState(Document document);
}
