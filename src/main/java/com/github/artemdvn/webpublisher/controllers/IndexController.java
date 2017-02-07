package com.github.artemdvn.webpublisher.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
    public String startPage(Model model) {
		return "index";
    }
	
	@RequestMapping("/error")
    public String errorPage(Model model) {
		return "error";
    }	
}
