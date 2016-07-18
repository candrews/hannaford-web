package com.integralblue.hannaford.web.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.integralblue.hannaford.web.model.Breadcrumb;
import com.integralblue.hannaford.web.service.BreadcrumbParserService;

@Service
public class BreadcrumbParserServiceImpl implements BreadcrumbParserService {

	@Override
	public ImmutableList<Breadcrumb> getBreadcrumbs(Document document) {
		final Builder<Breadcrumb> builder = ImmutableList.<Breadcrumb>builder();
    	for(Element element : document.getElementsByClass("crumb")){
    		// urls need to be relative
    		String href = element.getElementsByTag("a").attr("href").replaceFirst("https?://www\\.hannaford\\.com", "");
    		String name = element.text();
    		if(Strings.isNullOrEmpty(name)){
    			break;
    		}
    		builder.add(Breadcrumb.builder()
    				.name(name)
    				.href(href)
    				.build());
    	}
    	return builder.build();
	}

}
