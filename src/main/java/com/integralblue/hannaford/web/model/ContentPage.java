package com.integralblue.hannaford.web.model;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class ContentPage implements Page {
	@NonNull
	String title;
	
	@NonNull
	String htmlContent;
	
	@NonNull
	@Singular
	ImmutableList<Breadcrumb> breadcrumbs;

}
