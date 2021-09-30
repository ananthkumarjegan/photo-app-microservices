package com.javan.photoapp.users.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javan.photoapp.users.entity.User;
import com.javan.photoapp.users.entity.UserCreateRequestModel;
import com.javan.photoapp.users.entity.UserResponseModel;
import com.javan.photoapp.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment environment;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	UserService userService;

	@GetMapping("/status/check")
	public String statusCheck() {
		return environment.getProperty("spring.application.name") + " runs on port : "
				+ environment.getProperty("local.server.port") + " Expire timing = "
				+ environment.getProperty("token.expiration_time") + " Token = "
				+ environment.getProperty("token.secret");
	}

	@PostMapping("/create")
	public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserCreateRequestModel userRequestEntity) {
		User user = modelMapper.map(userRequestEntity, User.class);
		UserResponseModel createdUser = userService.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable Long userId) {
		UserResponseModel user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

//	@GetMapping("/getByUserName/{userName}")
//	public ResponseEntity<UserResponseModel> getUserByUserName(@PathVariable String userName) {
//		UserResponseModel user = userService.getUserByUserName(userName);
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}

	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<UserResponseModel> getUserByEmail(@PathVariable String email) {
		UserResponseModel user = userService.getUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
