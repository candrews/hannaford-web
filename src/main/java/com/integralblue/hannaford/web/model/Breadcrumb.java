package com.integralblue.hannaford.web.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Breadcrumb {
	@NonNull
	String name;
	
	@NonNull
	String href;
}
