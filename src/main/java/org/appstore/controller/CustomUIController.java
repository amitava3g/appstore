package org.appstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomUIController {
	
	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/templates/index.html";
	}
}
