package com.sparta.auth.application.service;


import com.sparta.user.dto.user_dto.UserCreateDto;
import com.sparta.user.dto.user_dto.UserDto;
import java.util.Optional;

public interface UserService {

  void createUser(UserCreateDto userCreateDto);

  Optional<UserDto> getUserDto(String username);
}
