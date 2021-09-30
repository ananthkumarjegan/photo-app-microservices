package com.javan.photoapp.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class PhotoAppSpringApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppSpringApiGatewayApplication.class, args);
	}

}
