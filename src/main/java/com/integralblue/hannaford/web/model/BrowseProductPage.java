package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class BrowseProductPage implements Page {
	@NonNull
	@Singular
	ImmutableMap<String, ImmutableList<BrowseProductCategory>> categories;
	
	@NonNull
	@Singular
	ImmutableList<Breadcrumb> breadcrumbs;
}
