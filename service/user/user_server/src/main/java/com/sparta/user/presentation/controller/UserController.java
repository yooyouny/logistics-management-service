package com.sparta.user.presentation.controller;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.application.service.UserService;
import com.sparta.user.dto.user_dto.UserDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResponseEntity<ResponseBody<UserInfo>> getUserInfo(@AuthenticationPrincipal JwtClaim claim) {
    return ResponseEntity.ok(new SuccessResponseBody<>(userService.getUserInfo(claim.getUserId())));
  }
}
