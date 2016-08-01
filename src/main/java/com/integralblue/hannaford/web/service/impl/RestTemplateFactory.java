package com.integralblue.hannaford.web.service.impl;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
	
	private static final List<String> PROXY_COOKIE_NAMES = Arrays.asList("PIPELINE_SESSION_ID","USER_SESSION_VALIDATE_COOKIE","HFD-LoadBalance","JSESSIONID","f5avrbbbbbbbbbbbbbbbb","SECURE_SESSION_ID");
	
    private RestTemplate restTemplate;
    
    private class Interceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
        	if(request.getURI().getPath().endsWith(".cmd") || request.getURI().getPath().endsWith(".jsp") || request.getURI().getPath().equals("/")){
	        	HttpServletRequest servletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	            HttpHeaders headers = request.getHeaders();
	        	for(String proxyCookieName : PROXY_COOKIE_NAMES){
	            	Cookie proxyCookie = WebUtils.getCookie(servletRequest, proxyCookieName);
	                if(proxyCookie!=null){
	                	if(headers.get(HttpHeaders.COOKIE)==null){
	                		headers.set(HttpHeaders.COOKIE, proxyCookie.getName() + "=" + proxyCookie.getValue());
	                	}else{
	                		headers.set(HttpHeaders.COOKIE, headers.get(HttpHeaders.COOKIE).get(0) + "; " + proxyCookie.getName() + "=" + proxyCookie.getValue());
	                	}
	                }
	        	}
	            ClientHttpResponse response = execution.execute(request, body);
	            HttpServletResponse servletResponse = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
	            List<String> cookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
	            if(cookieHeaders!=null){
	            	for(String cookieHeader : cookieHeaders){
	            		for(HttpCookie cookie : HttpCookie.parse(cookieHeader)){
	            			if(PROXY_COOKIE_NAMES.contains(cookie.getName())){
	            				Cookie responseCookie = new Cookie(cookie.getName(),cookie.getValue());
	            				responseCookie.setPath("/");
	            				servletResponse.addCookie(responseCookie);
	            			}
	            		}
	            	}
	            }
	            return response;
        	}else{
        		return execution.execute(request, body);
        	}
        }
    }
 
    public RestTemplate getObject() {
        return restTemplate;
    }
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }
    public boolean isSingleton() {
        return true;
    }
 
    public void afterPropertiesSet() {
        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new Interceptor()));
    }
    
}