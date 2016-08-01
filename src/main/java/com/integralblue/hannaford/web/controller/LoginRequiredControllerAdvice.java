package com.integralblue.hannaford.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.exception.LoginRequiredException;

@ControllerAdvice
public class LoginRequiredControllerAdvice {
	@ExceptionHandler(LoginRequiredException.class)
	public View loginRequired(HttpServletRequest request) throws UnsupportedEncodingException{
		return new RedirectView(SiteUrlConstants.LOGIN + "?dest=" + UriUtils.encodeQueryParam(getRelativeUrlWithQueryString(request), "UTF-8"), true);
	}
    
    private static String getRelativeUrlWithQueryString(HttpServletRequest request){
    	String relativeUrl = request.getRequestURI();
    	if(request.getQueryString()!=null){
    		relativeUrl+="?" + request.getQueryString();
    	}
    	return relativeUrl;
    }
}
