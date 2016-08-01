package com.integralblue.hannaford.web.controller;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.exception.LoginRequiredException;
import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;

@Controller
public class MyAccountController {
	@Autowired
	private PageService pageService;
	@Autowired
	private PageStateParserService pageStateParserService;
	
	@RequestMapping(method=RequestMethod.GET,value={SiteUrlConstants.MY_ACCOUNT})
	public String myAccount(Model model){
		Document document = pageService.getPage(SiteUrlConstants.MY_ACCOUNT);
		PageState pageState = pageStateParserService.getPageState(document);
		if(pageState.getUserInfo()==null){
			throw new LoginRequiredException();
		}
    	model.addAttribute("pageState",pageState);
    	return "myAccount";
	}

}
