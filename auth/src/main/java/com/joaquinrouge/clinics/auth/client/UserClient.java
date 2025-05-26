package com.joaquinrouge.clinics.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joaquinrouge.clinics.auth.dto.UserModel;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
	
	@GetMapping("/api/users/username/{username}")
	UserModel findByUsername(@PathVariable String username);
}
