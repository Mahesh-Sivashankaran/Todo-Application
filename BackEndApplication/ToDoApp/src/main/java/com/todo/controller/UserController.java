package com.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.responseModal.UserStatusResponse;
import com.todo.service.UserService;


@RestController
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/validUser", method = RequestMethod.GET)
	public UserStatusResponse validateUser(@RequestParam("user") String username, @RequestParam("pass") String password) {
		return userService.validateUser(username, password);
	}

}
