package com.sparta.auth.application.service;

import com.sparta.commons.domain.jwt.JwtClaim;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthInternalService {

  private final UserService userService;

  public JwtClaim verifyToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (isAuthenticatedAndMaster(authentication)) {
      return (JwtClaim) authentication.getPrincipal();
    }
    return null;
  }

  private boolean isAuthenticatedAndMaster(Authentication authentication) {
    if (authentication == null || !(authentication.getPrincipal() instanceof JwtClaim jwtClaim)) {
      return false;
    }
    if (!jwtClaim.getRole().equals("ROLE_MASTER")) { // ROLE_MASTER 가 아니면 바로 true 반환
      return true;
    }
    return userService.isMaster(jwtClaim.getUserId());
  }
}
