package com.integralblue.hannaford.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManifestController {
	@RequestMapping(method=RequestMethod.GET,value="manifest.html")
	public String manifest(){
		return "manifest";
	}
}
