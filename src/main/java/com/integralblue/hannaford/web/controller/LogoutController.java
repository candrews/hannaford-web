package com.integralblue.hannaford.web.controller;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;

@Controller
public class LogoutController {
	@Autowired
	private PageService pageService;
	@Autowired
	private PageStateParserService pageStateParserService;
	
	@RequestMapping(method=RequestMethod.GET,value={"user/logout.jsp","/user/logout.cmd"})
	public String getLogout(Model model){
		Document document = pageService.getPage(SiteUrlConstants.LOGOUT);
    	model.addAttribute("pageState",pageStateParserService.getPageState(document));
		return "logout";
	}

}
