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

import com.github.artemdvn.webpublisher.model.User;
import com.github.artemdvn.webpublisher.services.UserService;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/users")
    public String getAllUsers(Model model) {
		List<User> users = null;
		if (hasRole("ADOPS")){
			users = userService.getByRole("PUBLISHER");			
		} else {
			users = userService.getAll();
		}
		model.addAttribute("users", users);
		model.addAttribute("newUser", new User());
		return "users/users";
    }
	
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("newUser") @Valid User user,
								BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/users");
		
		if (bindingResult.hasErrors()) {
            logger.info("Errors during field fulfilling. Returning to users form");
            return modelAndView;
        }
		
		userService.create(user);
		return modelAndView;
	}
	
	@RequestMapping(value = "/users/edit", method = RequestMethod.GET)
	public ModelAndView editUserPage(@RequestParam(value = "id", required = true) Integer id) {
		ModelAndView modelAndView = new ModelAndView("users/editUser");
		User user = userService.get(id);
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	@RequestMapping(value = "/users/edit", method = RequestMethod.POST)
	public String editUser(@ModelAttribute @Valid User user, 
			BindingResult bindingResult,
			Model model,
			@RequestParam(value = "action", required = true) String action) {
		
		if (action.equals("save")) {
	    	if (bindingResult.hasErrors()) {
	            logger.info("Errors during field fulfilling. Returning to user form");
	            return "redirect:/users/edit?id=" + user.getId();
	        }
	    	userService.update(user);
	    }
	 
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/users/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam(value="id", required = true) Integer id, 
			@RequestParam(value = "action", required = true) String action) {
		User user = userService.get(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/users");
		if (action.equals("form")) {
			modelAndView = new ModelAndView("users/deleteUser");
			modelAndView.addObject("user", user);
	    }
		if (action.equals("delete")) {
			userService.delete(user);
	    }
	 
		return modelAndView;
	}
	
	 private boolean hasRole(String role) {
	        // get security context from thread local
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
	
}
