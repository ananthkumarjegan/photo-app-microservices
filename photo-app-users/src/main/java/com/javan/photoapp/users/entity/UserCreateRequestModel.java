package com.javan.photoapp.users.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserCreateRequestModel {
	
	@NotBlank(message = "user name should not be empty")
	private String userName;
	
	@NotBlank(message = "password should not be empty")
	private String password;
	
	@NotBlank(message = "email should not be empty")
	@Email(message = "email should be in correct format")
	private String email;
}
