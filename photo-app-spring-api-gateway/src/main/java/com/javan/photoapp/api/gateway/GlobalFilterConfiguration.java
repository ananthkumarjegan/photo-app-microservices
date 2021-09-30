package com.javan.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
	final Logger log = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

	@Order(1)
	@Bean
	public GlobalFilter secondFilter() {

		return (exchange, chain) -> {
			log.info("My Second Pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("My Second Post Filter");
			}));
		};
	}

	@Order(2)
	@Bean
	public GlobalFilter thirdFilter() {

		return (exchange, chain) -> {
			log.info("My Last Pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("My First Post Filter");
			}));
		};
	}
}
