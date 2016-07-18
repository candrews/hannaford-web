package com.integralblue.hannaford.web.service;

import org.jsoup.nodes.Document;

import com.google.common.collect.ImmutableList;
import com.integralblue.hannaford.web.model.Breadcrumb;

public interface BreadcrumbParserService {
	ImmutableList<Breadcrumb> getBreadcrumbs(Document document);
}
