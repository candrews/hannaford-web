package com.integralblue.hannaford.web.service.impl;

import java.math.BigDecimal;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integralblue.hannaford.web.exception.NoSearchResultsFoundException;
import com.integralblue.hannaford.web.model.SearchPage;
import com.integralblue.hannaford.web.model.SearchProduct;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.SearchPageService;

@Service
public class SearchPageServiceImpl implements SearchPageService {
	@Autowired
	private PageService pageService;
	@Autowired
	private BreadcrumbParserService breadcrumbParserService;

	@Override
	public SearchPage getSearchPage(String relativeUrl) throws NoSearchResultsFoundException {
		Document document = pageService.getPage(relativeUrl);
		
		if(document.getElementsByClass("search-result-info").size()==0){
			throw new NoSearchResultsFoundException();
		}
    	
    	final SearchPage.SearchPageBuilder builder = SearchPage.builder();
    	
    	builder.keyword(document.getElementsByClass("search-result-info").last().getElementsByTag("strong").last().text());
    	
    	builder.breadcrumbs(breadcrumbParserService.getBreadcrumbs(document));
    	
    	for(Element productCell : document.getElementsByClass("productDisplayTable")){
    		builder.searchProduct(
    				SearchProduct.builder()
    				.image(productCell.getElementsByClass("thirdparty_image").first().attr("src"))
    				.name(productCell.getElementsByClass("productName").first().text())
    				.overline(productCell.getElementsByClass("overline").first().text())
    				.price(new BigDecimal(productCell.getElementsByClass("item-price").attr("value")))
    				.href(productCell.getElementsByClass("overline-productName").attr("href"))
    				.build());
    	}
    	return builder.build();
	}

}
