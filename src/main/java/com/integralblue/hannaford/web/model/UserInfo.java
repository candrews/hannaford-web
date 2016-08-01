package com.integralblue.hannaford.web.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserInfo {
	@NonNull String id;
	@NonNull String firstName;
}
