package com.integralblue.hannaford.web.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.integralblue.hannaford.web.exception.StoreRequiredException;
import com.integralblue.hannaford.web.model.BrowseProductCategory;
import com.integralblue.hannaford.web.model.BrowseProductPage;
import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;
import com.integralblue.hannaford.web.service.BrowseProductPageService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;
import com.integralblue.hannaford.web.service.ProxyService;

@Service
public class BrowseProductPageServiceImpl implements BrowseProductPageService {
	@Autowired
	private PageService pageService;
	@Autowired
	private BreadcrumbParserService breadcrumbParserService;
	@Autowired
	private PageStateParserService pageStateParserService;

	@Override
	public BrowseProductPage getBrowseProductPage(String relativeUrl) {

		Document document = pageService.getPage(relativeUrl);
		PageState pageState = pageStateParserService.getPageState(document);
    	if(pageState.getStoreInfo()==null){
    		throw new StoreRequiredException();
    	}
		
    	
    	final BrowseProductPage.BrowseProductPageBuilder builder = BrowseProductPage.builder();
    	builder.breadcrumbs(breadcrumbParserService.getBreadcrumbs(document));
    	builder.pageState(pageState);
    	
    	for(Element outerCategory : document.getElementsByClass("department-category")){
    		String outerCategoryName = outerCategory.getElementsByClass("sectWrap").text().trim();
    		Builder<BrowseProductCategory> browseProductCategoryBuilder = ImmutableList.<BrowseProductCategory>builder();
    		for(Element category : outerCategory.getElementsByTag("a")){
    			browseProductCategoryBuilder.add(BrowseProductCategory.builder().name(category.text()).href(category.attr("href")).build());
    		}
    		builder.category(outerCategoryName, browseProductCategoryBuilder.build());
    	}
    	
    	return builder.build();
	}

}
