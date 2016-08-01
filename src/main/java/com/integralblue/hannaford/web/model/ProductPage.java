package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class ProductPage implements Page {
	@NonNull
	Product product;
	
	@NonNull
	@Singular
	ImmutableList<Breadcrumb> breadcrumbs;
	
	@NonNull
	PageState pageState;
}
