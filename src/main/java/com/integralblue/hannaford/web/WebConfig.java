package com.integralblue.hannaford.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.integralblue.hannaford.web.resource.AppCacheUserTransformer;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private ResourceProperties resourceProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/offline.manifest")
			.addResourceLocations(this.resourceProperties.getStaticLocations())
			.setCachePeriod(0)
			.resourceChain(resourceProperties.getChain().isCache())
			.addTransformer(appCacheUserTransformer());
	}

	@Bean
	public AppCacheUserTransformer appCacheUserTransformer() {
		return new AppCacheUserTransformer();
	}

}
