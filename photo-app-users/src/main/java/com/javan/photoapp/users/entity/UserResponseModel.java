package com.javan.photoapp.users.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseModel {

	private Long userId;
	private String userName;
	private String email;
	private List<AlbumResponseModel> albums;
	
}
