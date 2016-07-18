package com.integralblue.hannaford.web.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class BrowseProductCategory {
	@NonNull
	String name;
	@NonNull
	String href;
}
