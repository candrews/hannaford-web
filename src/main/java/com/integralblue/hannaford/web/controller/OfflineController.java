package com.integralblue.hannaford.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OfflineController {
    @RequestMapping(method=RequestMethod.GET, value={"/offline.html"})
	public String offline(){
		return "offline";
	}
}
