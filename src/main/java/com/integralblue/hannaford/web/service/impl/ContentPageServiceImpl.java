package com.integralblue.hannaford.web.service.impl;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.model.Breadcrumb;
import com.integralblue.hannaford.web.model.ContentPage;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;
import com.integralblue.hannaford.web.service.ContentPageService;
import com.integralblue.hannaford.web.service.PageService;

@Service
public class ContentPageServiceImpl implements ContentPageService {
	@Autowired
	private PageService pageService;
	@Autowired
	private BreadcrumbParserService breadcrumbParserService;

	@Override
	public ContentPage getContentPage(String relativeUrl) throws RestClientException {
		Document document = pageService.getPage(relativeUrl);
    	
    	return ContentPage.builder()
    			.title(document.title().replaceAll("\\| Hannaford", "").trim())
    			.htmlContent(document.getElementById("pageContentWrapperInner").outerHtml())
    			.breadcrumbs(breadcrumbParserService.getBreadcrumbs(document))
    			.build();
	}

	@Override
	public ContentPage getHomePage() throws RestClientException {
		Document document = pageService.getPage("/");
    	
    	return ContentPage.builder()
    			.title("Supermarket, Grocery, Coupons, Pharmacy, & Recipes")
    			.htmlContent(document.getElementById("pageContentWrapperInner").outerHtml())
    			.breadcrumb(Breadcrumb.builder().name("Home").href("/").build())
    			.build();
	}

}
