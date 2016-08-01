package com.integralblue.hannaford.web.model;

import java.net.URI;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class StoreInfo {
	@NonNull
	String name;
	
	int id;
	
	@NonNull
	String type;
	
	@NonNull
	String link;
}
