package com.integralblue.hannaford.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.exception.LoginException;
import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.service.LoginService;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private PageService pageService;
	@Autowired
	private PageStateParserService pageStateParserService;
	
	@RequestMapping(method=RequestMethod.GET,value={"user/login.jsp","/user/login.cmd"})
	public String getLogin(Model model, @RequestParam(required=false, defaultValue=SiteUrlConstants.MY_ACCOUNT) String dest, HttpServletResponse response){
		Document document = pageService.getPage(SiteUrlConstants.LOGIN);
		PageState pageState = pageStateParserService.getPageState(document);
		if(pageState.getUserInfo()==null){
	    	model.addAttribute("pageState",pageState);
	    	model.addAttribute("dest",dest);
			return "login";
		}else{
			response.setHeader("Turbolinks-Location", dest);
			return "redirect:" + dest;
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value={"user/login.jsp","/user/login.cmd"})
	public String postLogin(@RequestParam("userName") String username, @RequestParam("password") String password, @RequestParam("dest") String dest, Model model, HttpServletResponse response){
		try{
			loginService.login(username, password);
		}catch(LoginException e){
			return getLogin(model,dest,response);
		}
		response.setHeader("Turbolinks-Location", dest);
		return "redirect:" + dest;
	}

}
