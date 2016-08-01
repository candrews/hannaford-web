package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class SearchPage implements Page {
	@NonNull
	String keyword;
	
	@NonNull
	@Singular
	ImmutableList<SearchProduct> searchProducts;
	
	@NonNull
	@Singular
	ImmutableList<Breadcrumb> breadcrumbs;
	
	@NonNull
	PageState pageState;
}
