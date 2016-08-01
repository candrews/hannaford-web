package com.integralblue.hannaford.web.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import com.integralblue.hannaford.web.exception.HannafordSystemError;
import com.integralblue.hannaford.web.exception.LoginException;
import com.integralblue.hannaford.web.service.LoginService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.ProxyService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private ProxyService proxyService;
	@Autowired
	private PageService pageService;

	@Override
	public void login(String username, String password) throws RestClientException, LoginException {
		Document loginDocument = pageService.getPage("/user/login.jsp");
		String csrfTokenLoginForm = loginDocument.getElementById("CSRF_TOKEN_LOGIN_FORM").attr("value");
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("CSRF_TOKEN_LOGIN_FORM", csrfTokenLoginForm);
		map.add("userName", username);
		map.add("password", password);
		map.add("keepLoggedIn", "true");
		map.add("isLoginForm", "true");
		map.add("loginAction", "TRUE");
		map.add("form_state", "loginForm");
		map.add("dest", "https://www.hannaford.com/user/login.jsp?merge=false&keepLoggedIn=true");
		
    	ResponseEntity<String> responseEntity = proxyService.postForEntity("/user/login.cmd", map, String.class);
    	
    	if(responseEntity.getStatusCode()==HttpStatus.FOUND && responseEntity.getHeaders().getLocation().toString().startsWith("https://www.hannaford.com/user/login.jsp?merge=false&keepLoggedIn=true")){
    		return;
    	}else{
    	
	    	Document document = Jsoup.parse(responseEntity.getBody());
	    	
	    	if(document.title().equals("System Error | Hannaford")){
	    		throw new HannafordSystemError();
	    	}
	    	
    		throw new LoginException();
    	}
	}

}
