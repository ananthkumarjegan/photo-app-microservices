package com.javan.photoapp.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.javan.photoapp.users.entity.User;
import com.javan.photoapp.users.entity.UserResponseModel;

public interface UserService extends UserDetailsService {

	UserResponseModel createUser(User user);

	UserResponseModel getUserById(Long userId);
	
	UserResponseModel getUserByEmail(String email);
	
	UserResponseModel getUserByUserName(String userName);

}
