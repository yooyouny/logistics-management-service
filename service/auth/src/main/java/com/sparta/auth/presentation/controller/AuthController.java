package com.sparta.auth.presentation.controller;

import com.sparta.auth.application.dtos.SingInResponse;
import com.sparta.auth.application.service.AuthService;
import com.sparta.auth.presentation.dtos.SignInRequest;
import com.sparta.auth.presentation.dtos.SignUpRequest;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseBody<Void>> signUp(@RequestBody @Valid SignUpRequest request) {
    authService.signUp(request.username(), request.email(), request.password());
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ResponseBody<SingInResponse>> signUp(
      @RequestBody @Valid SignInRequest request) {
    SingInResponse response = authService.signIn(request.username(), request.password());
    return ResponseEntity.ok(new SuccessResponseBody<>(response));
  }
}
