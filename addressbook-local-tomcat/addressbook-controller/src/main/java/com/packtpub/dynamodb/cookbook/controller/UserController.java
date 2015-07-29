package com.packtpub.dynamodb.cookbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.packtpub.dynamodb.cookbook.addressbook.domain.User;
import com.packtpub.dynamodb.cookbook.addressbook.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/v1/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void register(@RequestBody User user) {

		userService.createUser(user);
	}

	@RequestMapping(value = "/v1/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void login(@RequestBody User user) {
		User fetchedUser = userService.getUserByEmail(user.getEmail());
		if (fetchedUser.getPassword().equalsIgnoreCase(user.getPassword())) {
			// do nothing
		} else {
			throw new RuntimeException("Invalid email or password");
		}
	}
}
