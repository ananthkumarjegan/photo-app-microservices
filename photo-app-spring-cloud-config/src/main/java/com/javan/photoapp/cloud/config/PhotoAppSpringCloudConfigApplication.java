package com.javan.photoapp.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PhotoAppSpringCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppSpringCloudConfigApplication.class, args);
	}

}
