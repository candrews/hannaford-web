package com.integralblue.hannaford.web.service.impl;

import java.math.BigDecimal;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integralblue.hannaford.web.model.ThumbnailPage;
import com.integralblue.hannaford.web.model.ThumbnailProduct;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;
import com.integralblue.hannaford.web.service.ThumbnailPageService;

@Service
public class ThumbnailPageServiceImpl implements ThumbnailPageService {
	@Autowired
	private PageService pageService;
	@Autowired
	private BreadcrumbParserService breadcrumbParserService;
	@Autowired
	private PageStateParserService pageStateParserService;

	@Override
	public ThumbnailPage getThumbnailPage(String relativeUrl) {
		Document document = pageService.getPage(relativeUrl);
    	
    	final ThumbnailPage.ThumbnailPageBuilder builder = ThumbnailPage.builder();
    	builder.title(document.getElementsByTag("h1").first().text().trim());
    	
    	builder.breadcrumbs(breadcrumbParserService.getBreadcrumbs(document));
    	builder.pageState(pageStateParserService.getPageState(document));
    	
    	builder.currentPage(Integer.parseInt(document.getElementsByClass("current-page").first().text()));
    	
    	for(Element productCell : document.select("td.productCell")){
    		Element container = productCell.parent();
    		builder.thumbnailProduct(ThumbnailProduct.builder()
    				.href(container.getElementsByClass("productImageLink").attr("href"))
    				.image(container.getElementsByClass("thirdparty_image").attr("src"))
    				.overline(container.getElementsByClass("overline").text().trim())
    				.name(container.getElementsByClass("productName").text().trim())
    				.unitPrice(container.getElementsByClass("unitPriceDisplay").text().replaceAll("Unit Price:", "").trim())
    				.price(new BigDecimal(container.getElementsByClass("item-price").attr("value")))
    				.build()
    				);
    	}
    	
    	return builder.build();
	}

}
