package com.javan.photoapp.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.javan.photoapp.users.entity.User;
import com.javan.photoapp.users.entity.UserCreateRequestModel;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class PhotoAppUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppUsersApplication.class, args);
	}

}
