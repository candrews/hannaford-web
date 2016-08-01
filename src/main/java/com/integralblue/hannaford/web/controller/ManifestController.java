package com.integralblue.hannaford.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManifestController {
	@RequestMapping("manifest.html")
	public String manifest(){
		return "manifest";
	}
}
