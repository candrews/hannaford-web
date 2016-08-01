package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;

public interface Page {

	ImmutableList<Breadcrumb> getBreadcrumbs();
	
	PageState getPageState();
}
