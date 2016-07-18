package com.integralblue.hannaford.web.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ThumbnailProduct {
	@NonNull
	String name;
	@NonNull
	String overline;
	@NonNull
	BigDecimal price;
	@NonNull
	String unitPrice;
	@NonNull
	String href;
	@NonNull
	String image;
}
