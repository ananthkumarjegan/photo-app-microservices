package com.javan.photoapp.users.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javan.photoapp.users.entity.AlbumResponseModel;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "albums-ws", fallbackFactory = AlbumFallBackFactory.class)
public interface AlbumsFeignClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}

@Component
class AlbumFallBackFactory implements FallbackFactory<AlbumsFeignClient> {

	@Override
	public AlbumsFeignClient create(Throwable cause) {
		return new AlbumFallBackClient(cause);
	}

}

@Slf4j
class AlbumFallBackClient implements AlbumsFeignClient {
	private Throwable cause;

	public AlbumFallBackClient(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		if (cause instanceof FeignException) {
			log.error("Error occurred when calling Album service with userId : " + id);
		} else {
			log.error("Error occurred when calling Album service :" + cause.getLocalizedMessage());
		}
		return new ArrayList<>();
	}

}
