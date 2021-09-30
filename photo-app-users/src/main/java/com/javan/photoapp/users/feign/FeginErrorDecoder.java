package com.javan.photoapp.users.feign;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeginErrorDecoder implements ErrorDecoder {

	private static String ErrorMessage = "Error occurred in %s .reason : %s";

	@Override
	public Exception decode(String methodKey, Response response) {
		return new ResponseStatusException(HttpStatus.valueOf(response.status()),
				String.format(ErrorMessage, methodKey, response.reason()));
	}

}
