package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class ThumbnailPage implements Page {
	@NonNull
	String title;
	
	@NonNull
	@Singular
	ImmutableList<Breadcrumb> breadcrumbs;

	@NonNull
	@Singular
	ImmutableList<ThumbnailProduct> thumbnailProducts;
	
	int currentPage;
	
	int pageSize;
	
	@NonNull
	PageState pageState;
}
