package com.sparta.auth.infrastructure.feign;

import com.sparta.auth.application.service.UserService;
import com.sparta.auth.infrastructure.configuration.UserFeignConfig;
import com.sparta.user.dto.user_dto.UserCreateDto;
import com.sparta.user.dto.user_dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", configuration = UserFeignConfig.class)
public interface UserClient extends UserService {

  @PostMapping("/internal/users")
  void createUser(@RequestBody UserCreateDto userCreateDto);

  @GetMapping("/internal/users")
  Optional<UserDto> getUserDto(@RequestParam(value = "username") String username);

  @GetMapping("/internal/users/{userId}")
  boolean isMaster(@PathVariable Long userId);
}
