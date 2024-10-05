package com.sparta.auth.presentation.controller;

import com.sparta.auth.application.service.AuthInternalService;
import com.sparta.commons.domain.jwt.JwtClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/auth")
public class AuthInternalController {

  private final AuthInternalService authInternalService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/verify")
  public JwtClaim verifyToken() {
    return authInternalService.verifyToken();
  }
}
