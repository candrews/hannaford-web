package com.integralblue.hannaford.web.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.model.PageState.PageStateBuilder;
import com.integralblue.hannaford.web.model.StoreInfo;
import com.integralblue.hannaford.web.model.UserInfo;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;

@Service
public class PageStateParserServiceImpl implements PageStateParserService {
	private static String URL_PREFIX = "http://www.hannaford.com";
	
	@Autowired
	private PageService pageService;

	@Override
	public PageState getPageState(Document document) {
	 	PageStateBuilder builder = PageState.builder();
		Element selectedStoreIdData = document.getElementsByClass("selectedStoreIdData").first();
		if(selectedStoreIdData!=null){
			builder.storeInfo(StoreInfo.builder()
					.id(Integer.parseInt(selectedStoreIdData.attr("value")))
					.name(document.getElementsByClass("storeName").first().text())
					.type(document.getElementsByClass("storeType").first().text())
					.link(removeHannafordPrefix(document.getElementsByClass("storeLink").attr("href")))
					.build());
		}
		Element userName = document.getElementsByClass("user-name").first();
		if(userName!=null){
			// the id isn't on all pages, so we have to do another request :(
			Document myAccountDocument = pageService.getPage(SiteUrlConstants.MY_ACCOUNT);
			String id = myAccountDocument.getElementsByClass("webID").first().text();
			
			builder.userInfo(UserInfo.builder()
					.id(id)
					.firstName(userName.text())
					.build());
		}
		return builder.build();
	}
	
	private static String removeHannafordPrefix(String link){
		if(org.springframework.util.StringUtils.startsWithIgnoreCase(link, URL_PREFIX)){
			return link.substring(URL_PREFIX.length());
		}else{
			return link;
		}
	}

}
