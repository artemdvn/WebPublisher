package com.github.artemdvn.webpublisher.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.artemdvn.webpublisher.model.App;
import com.github.artemdvn.webpublisher.model.User;
import com.github.artemdvn.webpublisher.services.AppService;
import com.github.artemdvn.webpublisher.services.UserService;

@Controller
public class AppController {
	
	private Logger logger = Logger.getLogger(AppController.class);
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/apps")
    public String getAllApps(Model model) {
		List<App> apps = null;
		if (hasRole("PUBLISHER")){
			User user = userService.getByName(getCurrentUsername());
			if (user != null) {
				apps = appService.getByUser(user);
			}
		} else {
			apps = appService.getAll();
		}
		model.addAttribute("apps", apps);
		model.addAttribute("newApp", new App());
		return "apps/apps";
    }
	
	@RequestMapping(value = "/apps/add", method = RequestMethod.POST)
	public ModelAndView addApp(@ModelAttribute("newApp") @Valid App app, BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView("redirect:/apps");

		if (bindingResult.hasErrors()) {
			logger.info("Errors during field fulfilling. Returning to apps form");
			return modelAndView;
		}
		
		String currentUsername = getCurrentUsername();
		if (currentUsername == null) {
			logger.info("No user authenticated. Returning to apps form");
			return modelAndView;
		}
		
		app.setUser(userService.getByName(currentUsername));		
		appService.create(app);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/apps/edit", method = RequestMethod.GET)
	public ModelAndView editAppPage(@RequestParam(value = "id", required = true) Integer id) {
		ModelAndView modelAndView = new ModelAndView("apps/editApp");
		App app = appService.get(id);
		modelAndView.addObject("app", app);
		return modelAndView;
	}
	
	@RequestMapping(value = "/apps/edit", method = RequestMethod.POST)
	public String editApp(@ModelAttribute @Valid App app, 
			BindingResult bindingResult,
			Model model,
			@RequestParam(value = "action", required = true) String action) {
		
		if (action.equals("save")) {
	    	if (bindingResult.hasErrors()) {
	            logger.info("Errors during field fulfilling. Returning to app form");
	            return "redirect:/apps/edit?id=" + app.getId();
	        }
	    	appService.update(app);
	    }
	 
		return "redirect:/apps";
	}
	
	@RequestMapping(value = "/apps/delete", method = RequestMethod.GET)
	public ModelAndView deleteApp(@RequestParam(value="id", required = true) Integer id, 
			@RequestParam(value = "action", required = true) String action) {
		App app = appService.get(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/apps");
		if (action.equals("form")) {
			modelAndView = new ModelAndView("apps/deleteApp");
			modelAndView.addObject("app", app);
	    }
		if (action.equals("delete")) {
			appService.delete(app);
	    }
	 
		return modelAndView;
	}
	
	private boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}
	
	private String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getName();
		}
		return null;
	}
	
}
