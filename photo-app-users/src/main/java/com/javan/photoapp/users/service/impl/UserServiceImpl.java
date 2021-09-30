package com.javan.photoapp.users.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javan.photoapp.users.entity.AlbumResponseModel;
import com.javan.photoapp.users.entity.User;
import com.javan.photoapp.users.entity.UserResponseModel;
import com.javan.photoapp.users.feign.AlbumsFeignClient;
import com.javan.photoapp.users.repository.UserRepository;
import com.javan.photoapp.users.service.UserService;

import feign.FeignException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
//	private RestTemplate restTemplate;
	AlbumsFeignClient albumsFeignClient;

//	@Autowired
//	private Environment env;

	@Override
	public UserResponseModel createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		User createdUser = userRepo.save(user);
		UserResponseModel userResponse = modelMapper.map(createdUser, UserResponseModel.class);
		return userResponse;
	}

	@Override
	public UserResponseModel getUserById(Long userId) {
		Optional<User> user = userRepo.findById(userId);

		if (!user.isPresent())
			throw new UsernameNotFoundException("User not found");

		UserResponseModel userResponse = modelMapper.map(user.get(), UserResponseModel.class);
//		String albumUrl = String.format(env.getProperty("albums.url"), userId.toString());
//		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumUrl, HttpMethod.GET,
//				null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//				});
//		List<AlbumResponseModel> albumList = albumsListResponse.getBody();
		List<AlbumResponseModel> albumList = null;
//		try {
		log.info("Before calling album service  ++++++++++++++++++++++++++++");
		albumList = albumsFeignClient.getAlbums(userId.toString());
		log.info("After calling album service  ++++++++++++++++++++++++++++");
//		} catch (FeignException e) {
//			log.error(e.getLocalizedMessage());
//		}
		userResponse.setAlbums(albumList);
		return userResponse;
	}

	@Override
	public UserResponseModel getUserByEmail(String email) {
		Optional<User> user = userRepo.findUserByEmail(email);
		UserResponseModel userResponse = modelMapper.map(user.orElse(new User()), UserResponseModel.class);
		return userResponse;
	}

	@Override
	public UserResponseModel getUserByUserName(String userName) {
		Optional<User> user = userRepo.findUserByUserName(userName);
		UserResponseModel userResponse = modelMapper.map(user.orElse(new User()), UserResponseModel.class);
		return userResponse;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByEmail(email);
//		UserResponseEntity userResponse = modelMapper.map(user.orElse(new User()), UserResponseEntity.class);
		if (!user.isPresent())
			throw new UsernameNotFoundException("User not found");
		User userDetails = user.get();
		return new org.springframework.security.core.userdetails.User(userDetails.getEmail(), userDetails.getPassword(),
				new ArrayList<>());
	}

}
