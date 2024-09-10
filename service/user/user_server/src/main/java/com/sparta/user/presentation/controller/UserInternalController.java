package com.sparta.user.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.user.application.service.UserInternalService;
import com.sparta.user.application.service.UserService;
import com.sparta.user.dto.user_dto.UserCreateDto;
import com.sparta.user.dto.user_dto.UserDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/users")
public class UserInternalController {

  private final UserInternalService userInternalService;

  @PostMapping
  public void createUser(@RequestBody UserCreateDto userCreateDto) {
    userInternalService.createUser(userCreateDto.username(), userCreateDto.email(), userCreateDto.encodedPassword());
  }

  @GetMapping
  public Optional<UserDto> getUserDto(@RequestParam(value = "username") String username) {
    return userInternalService.getUserDto(username);
  }
}
