package com.integralblue.hannaford.web.model;


import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Product {
	@NonNull
	String name;
	@NonNull
	String overline;
	@NonNull
	String unitPrice;
	@NonNull
	BigDecimal price;
	@NonNull
	String image;
}
