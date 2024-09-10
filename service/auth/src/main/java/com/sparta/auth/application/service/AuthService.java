package com.sparta.auth.application.service;

import com.sparta.auth.application.dtos.SingInResponse;
import com.sparta.auth.domain.JwtClaim;
import com.sparta.auth.exception.BusinessException;
import com.sparta.auth.exception.ErrorCode;
import com.sparta.auth.infrastructure.utils.JwtHandler;
import com.sparta.user.dto.user_dto.UserCreateDto;
import com.sparta.user.dto.user_dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final JwtHandler jwtHandler;

  public void signUp(String username, String email, String password) {
    userService.createUser(new UserCreateDto(username, email, passwordEncoder.encode(password)));
  }

  public SingInResponse signIn(String username, String password) {
    Optional<UserDto> userDto = userService.getUserDto(username);

    if (userDto.isEmpty() || !passwordEncoder.matches(password, userDto.get().password())) {
      throw new BusinessException(ErrorCode.SIGN_IN_FAIL);
    }

    String token = jwtHandler.createToken(
        JwtClaim.create(userDto.get().userId(), userDto.get().username(), userDto.get().role()));
    return new SingInResponse(token);
  }
}
