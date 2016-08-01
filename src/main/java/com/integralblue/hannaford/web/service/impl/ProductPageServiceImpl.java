package com.integralblue.hannaford.web.service.impl;

import java.math.BigDecimal;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.exception.StoreRequiredException;
import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.model.Product;
import com.integralblue.hannaford.web.model.ProductPage;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;
import com.integralblue.hannaford.web.service.ProductPageService;

import lombok.SneakyThrows;

@Service
public class ProductPageServiceImpl implements ProductPageService  {
	@Autowired
	private PageService pageService;
	@Autowired
	private BreadcrumbParserService breadcrumbParserService;
	@Autowired
	private PageStateParserService pageStateParserService;
	
	@Override
	@SneakyThrows
	public ProductPage getProductPage(String relativeUrl) throws RestClientException {
		Document document = pageService.getPage(relativeUrl);
    	document.getElementsByClass("productName").first().text();
    	PageState pageState = pageStateParserService.getPageState(document);
    	if(pageState.getStoreInfo()==null){
    		throw new StoreRequiredException();
    	}
    	
    	Product product = Product.builder()
    			.name(document.getElementsByClass("productName").first().text().trim())
    			.overline(document.getElementsByClass("overline").first().text().trim())
    			.price(new BigDecimal(document.getElementsByAttributeValue("name", "displayPrice").attr("value")))
    			.unitPrice(document.getElementById("unitPrice").text().trim())
    			.image(document.getElementsByClass("thirdparty_image").attr("src"))
    			.build();
    	
    	return ProductPage.builder()
    			.product(product)
    			.breadcrumbs(breadcrumbParserService.getBreadcrumbs(document))
    	    	.pageState(pageState)
    			.build();
	}
}
