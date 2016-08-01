package com.integralblue.hannaford.web.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PageState {
	StoreInfo storeInfo;
	
	UserInfo userInfo;
}
